package pl.kacper.misterski.rangestats.feature.settings.ui.settings

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import pl.kacper.misterski.rangestats.core.domain.Constants
import pl.kacper.misterski.rangestats.core.domain.converter.UnitConverter
import pl.kacper.misterski.rangestats.feature.settings.domain.usecase.DeleteWeaponUseCase
import pl.kacper.misterski.rangestats.feature.settings.domain.usecase.GetUserProfileUseCase
import pl.kacper.misterski.rangestats.feature.settings.domain.usecase.GetWeaponsUseCase
import pl.kacper.misterski.rangestats.feature.settings.domain.usecase.UpdateUserProfileUseCase
import kotlin.time.Duration.Companion.milliseconds

class SettingsViewModel(
    private val getWeaponsUseCase: GetWeaponsUseCase,
    private val getUserProfileUseCase: GetUserProfileUseCase,
    private val updateUserProfileUseCase: UpdateUserProfileUseCase,
    private val deleteWeaponUseCase: DeleteWeaponUseCase,
    private val unitConverter: UnitConverter,
) : ViewModel() {

    private val _uiModel = MutableStateFlow(SettingsUiModel())
    val uiModel: StateFlow<SettingsUiModel> = _uiModel.asStateFlow()

    private var persistDistanceJob: Job? = null // TODO review this

    fun onAction(action: SettingsAction) {
        when (action) {
            SettingsAction.IncrementDistance -> updateDistance(Constants.SESSION_DISTANCE_STEP)
            SettingsAction.DecrementDistance -> updateDistance(-Constants.SESSION_DISTANCE_STEP)
            SettingsAction.ShowDistanceEditDialog -> showDistanceEditDialog()
            SettingsAction.HideDistanceEditDialog -> hideDistanceEditDialog()
            is SettingsAction.DistanceInputChanged -> updateDistanceInput(action.text)
            SettingsAction.ConfirmDistanceInput -> confirmDistanceInput()
            is SettingsAction.UnitSystemChanged -> updateUnits(action)
            SettingsAction.OnStart -> fetchData()
            is SettingsAction.DeleteWeapon -> deleteWeapon(action.name)
        }
    }

    private fun fetchData() {
        viewModelScope.launch {
            _uiModel.update { it.copy(isLoading = true) }
            val profile = getUserProfileUseCase().catch { }.firstOrNull()
            if (profile == null) {
                _uiModel.update { it.copy(isLoading = false) }
                return@launch
            }
            val weapons = getWeaponsUseCase().catch { emit(emptyList()) }.first()
            val distanceDisplay = profile.toDistanceDisplayUiModel(unitConverter)
            _uiModel.update {
                it.copy(
                    isLoading = false,
                    profile = profile,
                    weapons = weapons.map { weapon -> weapon.toUiModel() },
                    distanceDisplay = distanceDisplay,
                )
            }
        }
    }

    private fun updateDistance(deltaInCurrentUnit: Int) {
        val display = _uiModel.value.distanceDisplay
        val units = _uiModel.value.profile.units
        val newDisplayValue = (display.value + deltaInCurrentUnit).coerceIn(display.min, display.max)
        val newMeters = unitConverter.toMeters(newDisplayValue, units)
            .coerceIn(Constants.SESSION_DISTANCE_MIN, Constants.SESSION_DISTANCE_MAX)
        applyDistance(newMeters)
    }

    private fun showDistanceEditDialog() {
        val currentText = _uiModel.value.distanceDisplay.value.toString()
        _uiModel.update {
            it.copy(
                distanceEdit = it.distanceEdit.copy(
                    isVisible = true,
                    inputText = currentText,
                    isInputValid = true,
                ),
            )
        }
    }

    private fun hideDistanceEditDialog() {
        _uiModel.update { it.copy(distanceEdit = it.distanceEdit.copy(isVisible = false)) }
    }

    private fun updateDistanceInput(text: String) {
        val digitsOnly = text.filter { it.isDigit() }
        val parsed = digitsOnly.toIntOrNull()
        val display = _uiModel.value.distanceDisplay
        val isValid = parsed != null && parsed in display.min..display.max
        _uiModel.update {
            it.copy(distanceEdit = it.distanceEdit.copy(inputText = digitsOnly, isInputValid = isValid))
        }
    }

    private fun confirmDistanceInput() {
        val parsedDisplay = _uiModel.value.distanceEdit.inputText.toIntOrNull() ?: return
        val units = _uiModel.value.profile.units
        val newMeters = unitConverter.toMeters(parsedDisplay, units)
            .coerceIn(Constants.SESSION_DISTANCE_MIN, Constants.SESSION_DISTANCE_MAX)
        applyDistance(newMeters)
        hideDistanceEditDialog()
    }

    private fun applyDistance(newDistance: Int) {
        val updatedProfile = _uiModel.value.profile.copy(defaultDistanceMeters = newDistance)
        val updatedDisplay = updatedProfile.toDistanceDisplayUiModel(unitConverter)
        _uiModel.update { it.copy(profile = updatedProfile, distanceDisplay = updatedDisplay) }
        persistDistanceJob?.cancel()
        persistDistanceJob = viewModelScope.launch {
            delay(PERSIST_DEBOUNCE_MS.milliseconds)
            updateUserProfileUseCase(updatedProfile).catch { }.collect()
        }
    }

    private fun updateUnits(action: SettingsAction.UnitSystemChanged) {
        val updatedProfile = _uiModel.value.profile.copy(units = action.units)
        val updatedDisplay = updatedProfile.toDistanceDisplayUiModel(unitConverter)
        _uiModel.update { it.copy(profile = updatedProfile, distanceDisplay = updatedDisplay) }
        viewModelScope.launch { updateUserProfileUseCase(updatedProfile).catch { }.collect() }
    }

    private fun deleteWeapon(name: String) {
        viewModelScope.launch {
            deleteWeaponUseCase(name).catch { }.collect()
            loadWeapons()
        }
    }

    private suspend fun loadWeapons() {
        val weapons = getWeaponsUseCase().catch { emit(emptyList()) }.first().map { weapon -> weapon.toUiModel() }
        _uiModel.update { it.copy(weapons = weapons) }
    }

    companion object {
        private const val PERSIST_DEBOUNCE_MS = 300L
    }
}
