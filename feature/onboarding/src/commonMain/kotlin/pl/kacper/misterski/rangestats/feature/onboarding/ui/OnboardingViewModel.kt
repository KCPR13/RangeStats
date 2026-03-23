package pl.kacper.misterski.rangestats.feature.onboarding.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.jetbrains.compose.resources.getString
import pl.kacper.misterski.rangestats.core.data.datasource.permission.PermissionDataSource
import pl.kacper.misterski.rangestats.core.domain.enums.AppPermission
import pl.kacper.misterski.rangestats.core.domain.enums.PermissionStatus
import pl.kacper.misterski.rangestats.core.domain.models.UserProfile
import pl.kacper.misterski.rangestats.feature.onboarding.domain.usecase.CompleteOnboardingUseCase
import rangestats.feature.onboarding.generated.resources.Res
import rangestats.feature.onboarding.generated.resources.onboarding_placeholder_distance

class OnboardingViewModel(
    private val completeOnboarding: CompleteOnboardingUseCase,
    private val permissionDataSource: PermissionDataSource, //TODO datasource in vm?
) : ViewModel() {

    private val _uiModel = MutableStateFlow(OnboardingUiModel())
    val uiModel: StateFlow<OnboardingUiModel> = _uiModel.asStateFlow()

    fun onAction(action: OnboardingAction) {
        when (action) {
            is OnboardingAction.NextPage -> advancePage()
            is OnboardingAction.Skip -> complete()
            is OnboardingAction.Complete -> complete()
            is OnboardingAction.UpdateDisplayName -> _uiModel.update { it.copy(displayName = action.name) }
            is OnboardingAction.SelectUnitSystem -> _uiModel.update { it.copy(unitSystem = action.system) }
            OnboardingAction.DecrementDistance -> decrementDistance()
            OnboardingAction.IncrementDistance -> incrementDistance()
            OnboardingAction.AddNewWeapon -> TODO()
            is OnboardingAction.CameraPermissionResult -> handleCameraResult(action.status)
            is OnboardingAction.LocationPermissionResult -> handleLocationResult(action.status)
        }
    }

    private fun handleCameraResult(status: PermissionStatus) {
        when (status) {
            PermissionStatus.GRANTED -> advancePage()
            PermissionStatus.PERMANENTLY_DENIED -> _uiModel.update {
                it.copy(showCameraRequired = true, showCameraPermanentlyDenied = true)
            }

            else -> _uiModel.update {
                it.copy(
                    showCameraRequired = true,
                    showCameraPermanentlyDenied = false
                )
            }
        }
    }

    private fun handleLocationResult(status: PermissionStatus) {
        advancePage()
    }

    private fun incrementDistance() {
        val newDistance = _uiModel.value.defaultDistanceMeters.plus(DISTANCE_STEP_IN_METERS)
        viewModelScope.launch {
            _uiModel.update {
                it.copy(
                    defaultDistanceMeters = newDistance,
                    distanceLabel = getString(
                        Res.string.onboarding_placeholder_distance,
                        newDistance
                    ),
                )
            }
        }
    }

    private fun decrementDistance() {
        val newDistance = _uiModel.value.defaultDistanceMeters.minus(DISTANCE_STEP_IN_METERS)
        viewModelScope.launch {
            _uiModel.update {
                it.copy(
                    defaultDistanceMeters = newDistance,
                    distanceLabel = getString(
                        Res.string.onboarding_placeholder_distance,
                        newDistance
                    ),
                )
            }
        }
    }

    private fun advancePage() {
        val entries = OnboardingPage.entries
        val current = _uiModel.value.currentPage
        val nextIndex = current.ordinal.plus(1)

        if (nextIndex < entries.size) {
            val newPage = entries[nextIndex]
            _uiModel.update { it.copy(currentPage = newPage) }
            checkIfPermissionAlreadyGranted(newPage)
        } else {
            complete()
        }
    }

    fun checkCameraPermission() {
        if (_uiModel.value.currentPage != OnboardingPage.CAMERA) return
        checkIfPermissionAlreadyGranted(OnboardingPage.CAMERA)
    }


    private fun checkIfPermissionAlreadyGranted(page: OnboardingPage) {
        val permission = when (page) {
            OnboardingPage.CAMERA -> AppPermission.CAMERA
            OnboardingPage.LOCATION -> AppPermission.LOCATION
            else -> return
        }
        viewModelScope.launch {
            if (permissionDataSource.getStatus(permission) == PermissionStatus.GRANTED) {
                advancePage()
            }
        }
    }

    private fun complete() {
        val state = _uiModel.value
        viewModelScope.launch {
            completeOnboarding(
                UserProfile(
                    displayName = state.displayName,
                    units = state.unitSystem,
                    defaultDistanceMeters = state.defaultDistanceMeters,
                )
            )
            _uiModel.update { it.copy(isCompleted = true) }
        }
    }

    companion object {
        private const val DISTANCE_STEP_IN_METERS = 5
    }
}
