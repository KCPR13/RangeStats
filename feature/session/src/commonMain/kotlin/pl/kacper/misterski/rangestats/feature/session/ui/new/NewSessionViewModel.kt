package pl.kacper.misterski.rangestats.feature.session.ui.new

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import pl.kacper.misterski.rangestats.feature.session.domain.usecase.StartSessionUseCase

class NewSessionViewModel(
    private val startSession: StartSessionUseCase,
) : ViewModel() {

    private val _uiModel = MutableStateFlow(NewSessionUiModel())
    val uiModel: StateFlow<NewSessionUiModel> = _uiModel.asStateFlow()

    private var onSessionStarted: ((String) -> Unit)? = null

    fun setOnSessionStarted(callback: (String) -> Unit) {
        onSessionStarted = callback
    }

    fun onAction(action: NewSessionAction) {
        when (action) {
            is NewSessionAction.LocationChanged -> updateLocation(action.name)
            is NewSessionAction.WeaponSelected -> selectWeapon(action.weaponId)
            NewSessionAction.IncrementDistance -> changeDistance(DISTANCE_STEP)
            NewSessionAction.DecrementDistance -> changeDistance(-DISTANCE_STEP)
            is NewSessionAction.TargetTypeSelected -> _uiModel.update { it.copy(targetType = action.type) }
            NewSessionAction.StartSession -> startNewSession()
            NewSessionAction.Back -> Unit
        }
    }

    private fun updateLocation(name: String) {
        _uiModel.update { it.copy(locationName = name, canStart = name.isNotBlank() && it.selectedWeaponName != null) }
    }

    private fun selectWeapon(weaponId: String) {
        _uiModel.update { it.copy(selectedWeaponName = weaponId, canStart = it.locationName.isNotBlank()) }
    }

    private fun changeDistance(delta: Int) {
        val newDist = (_uiModel.value.distanceMeters + delta).coerceIn(DISTANCE_MIN, DISTANCE_MAX)
        _uiModel.update { it.copy(distanceMeters = newDist) }
    }

    private fun startNewSession() {
        val state = _uiModel.value
        val weaponId = state.selectedWeaponName ?: return
        viewModelScope.launch {
            _uiModel.update { it.copy(isLoading = true) }
            startSession(
                weaponId = weaponId,
                locationName = state.locationName,
                distanceMeters = state.distanceMeters,
                targetType = state.targetType,
            ).onSuccess { session ->
               // onSessionStarted?.invoke(session.id) TODO whats that?
            }.onFailure {
                _uiModel.update { it.copy(isLoading = false) }
            }
        }
    }

    //TODO global constats?
    companion object {
        private const val DISTANCE_STEP = 5
        private const val DISTANCE_MIN = 5
        private const val DISTANCE_MAX = 300
    }
}
