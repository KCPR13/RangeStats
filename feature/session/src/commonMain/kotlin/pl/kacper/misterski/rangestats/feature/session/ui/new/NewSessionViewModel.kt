package pl.kacper.misterski.rangestats.feature.session.ui.new

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import pl.kacper.misterski.rangestats.core.domain.Constants
import pl.kacper.misterski.rangestats.feature.session.domain.usecase.StartSessionUseCase

class NewSessionViewModel(
    private val startSessionUseCase: StartSessionUseCase,
) : ViewModel() {

    private val _uiModel = MutableStateFlow(NewSessionUiModel())
    val uiModel: StateFlow<NewSessionUiModel> = _uiModel.asStateFlow()

    fun onAction(action: NewSessionAction) {
        when (action) {
            is NewSessionAction.LocationChanged -> updateLocation(action.name)
            is NewSessionAction.WeaponSelected -> selectWeapon(action.weaponName)
            NewSessionAction.IncrementDistance -> changeDistance(Constants.SESSION_DISTANCE_STEP)
            NewSessionAction.DecrementDistance -> changeDistance(-Constants.SESSION_DISTANCE_STEP)
            is NewSessionAction.TargetTypeSelected -> _uiModel.update { it.copy(targetType = action.type) }
            NewSessionAction.StartSession -> startNewSession()
            NewSessionAction.Back -> Unit
            NewSessionAction.NavigationHandled -> _uiModel.update { it.copy(navigateToActiveSession = null) }
        }
    }

    private fun updateLocation(name: String) {
        _uiModel.update { it.copy(locationName = name, canStart = name.isNotBlank() && it.selectedWeaponName != null) }
    }

    private fun selectWeapon(weaponName: String) {
        _uiModel.update { it.copy(selectedWeaponName = weaponName, canStart = it.locationName.isNotBlank() && weaponName.isNotBlank()) }
    }

    private fun changeDistance(delta: Int) {
        val newDist = (_uiModel.value.distanceMeters + delta).coerceIn(Constants.SESSION_DISTANCE_MIN, Constants.SESSION_DISTANCE_MAX)
        _uiModel.update { it.copy(distanceMeters = newDist) }
    }

    private fun startNewSession() {
        val state = _uiModel.value
        val weaponName = state.selectedWeaponName ?: return
        viewModelScope.launch {
            _uiModel.update { it.copy(isLoading = true) }
            startSessionUseCase(
                weaponName = weaponName,
                locationName = state.locationName,
                distanceMeters = state.distanceMeters,
                targetType = state.targetType,
            ).onSuccess { session ->
                _uiModel.update { it.copy(isLoading = false, navigateToActiveSession = session.id) }
            }.onFailure {
                _uiModel.update { it.copy(isLoading = false) }
            }
        }
    }

}
