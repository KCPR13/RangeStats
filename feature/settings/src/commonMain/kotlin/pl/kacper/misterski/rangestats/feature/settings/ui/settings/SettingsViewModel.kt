package pl.kacper.misterski.rangestats.feature.settings.ui.settings

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import pl.kacper.misterski.rangestats.feature.settings.domain.usecase.GetUserProfileUseCase
import pl.kacper.misterski.rangestats.feature.settings.domain.usecase.GetWeaponsUseCase
import pl.kacper.misterski.rangestats.feature.settings.domain.usecase.UpdateUserProfileUseCase

class SettingsViewModel(
    private val getWeapons: GetWeaponsUseCase,
    private val getUserProfile: GetUserProfileUseCase,
    private val updateUserProfile: UpdateUserProfileUseCase,
) : ViewModel() {

    private val _uiModel = MutableStateFlow(SettingsUiModel())
    val uiModel: StateFlow<SettingsUiModel> = _uiModel.asStateFlow()

    init {
        load() // TODO onStart
    }

    fun onAction(action: SettingsAction) {
        when (action) {
            SettingsAction.IncrementDistance -> updateDistance(DISTANCE_STEP)
            SettingsAction.DecrementDistance -> updateDistance(-DISTANCE_STEP)
            is SettingsAction.UnitSystemChanged -> updateUnits(action)
        }
    }

    private fun load() {
        viewModelScope.launch {
            _uiModel.update { it.copy(isLoading = true) }
            val profile = getUserProfile().getOrElse { return@launch }
            val weapons = getWeapons().getOrElse { emptyList() }
            _uiModel.update { it.copy(isLoading = false, profile = profile, weapons = weapons) }
        }
    }

    private fun updateDistance(delta: Int) {
        val current = _uiModel.value.profile
        val newDistance = (current.defaultDistanceMeters + delta).coerceIn(DISTANCE_MIN, DISTANCE_MAX)
        val updated = current.copy(defaultDistanceMeters = newDistance)
        _uiModel.update { it.copy(profile = updated) }
        viewModelScope.launch { updateUserProfile(updated) }
    }

    private fun updateUnits(action: SettingsAction.UnitSystemChanged) {
        val updated = _uiModel.value.profile.copy(units = action.units)
        _uiModel.update { it.copy(profile = updated) }
        viewModelScope.launch { updateUserProfile(updated) }
    }

    companion object {
        private const val DISTANCE_STEP = 5
        private const val DISTANCE_MIN = 5
        private const val DISTANCE_MAX = 300
    }
}
