package pl.kacper.misterski.rangestats.feature.onboarding.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import pl.kacper.misterski.rangestats.core.domain.enums.AppPermission
import pl.kacper.misterski.rangestats.core.domain.enums.PermissionStatus
import pl.kacper.misterski.rangestats.core.domain.models.UserProfile
import pl.kacper.misterski.rangestats.feature.onboarding.domain.usecase.CheckPermissionStatusUseCase
import pl.kacper.misterski.rangestats.feature.onboarding.domain.usecase.CompleteOnboardingUseCase
import pl.kacper.misterski.rangestats.feature.onboarding.domain.usecase.GetWeaponsUseCase

class OnboardingViewModel(
    private val completeOnboardingUseCase: CompleteOnboardingUseCase,
    private val checkPermissionStatusUseCase: CheckPermissionStatusUseCase,
    private val getWeaponsUseCase: GetWeaponsUseCase,
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
            OnboardingAction.AddNewWeapon -> _uiModel.update { it.copy(navigateToAddWeapon = true) }
            OnboardingAction.NavigationHandled -> _uiModel.update { it.copy(isCompleted = false) }
            OnboardingAction.NavigateToAddWeapon -> _uiModel.update { it.copy(navigateToAddWeapon = false) }
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
        val current = _uiModel.value
        val newDistance = (current.defaultDistanceMeters + DISTANCE_STEP_IN_METERS).coerceIn(current.minDistance, current.maxDistance)
        _uiModel.update { it.copy(defaultDistanceMeters = newDistance, distanceLabel = "$newDistance m") }
    }

    private fun decrementDistance() {
        val current = _uiModel.value
        val newDistance = (current.defaultDistanceMeters - DISTANCE_STEP_IN_METERS).coerceIn(current.minDistance, current.maxDistance)
        _uiModel.update { it.copy(defaultDistanceMeters = newDistance, distanceLabel = "$newDistance m") }
    }

    private fun advancePage() {
        val entries = OnboardingPage.entries
        val current = _uiModel.value.currentPage
        val nextIndex = current.ordinal.plus(1)

        if (nextIndex < entries.size) {
            val newPage = entries[nextIndex]
            _uiModel.update { it.copy(currentPage = newPage) }
            checkIfPermissionAlreadyGranted(newPage)
            if (newPage == OnboardingPage.ARSENAL) loadWeapons()
        } else {
            complete()
        }
    }

    fun checkCameraPermission() {
        if (_uiModel.value.currentPage != OnboardingPage.CAMERA) return
        checkIfPermissionAlreadyGranted(OnboardingPage.CAMERA)
    }

    fun refreshArsenal() {
        if (_uiModel.value.currentPage != OnboardingPage.ARSENAL) return
        loadWeapons()
    }

    private fun loadWeapons() {
        viewModelScope.launch {
            getWeaponsUseCase().onSuccess { weapons ->
                _uiModel.update { it.copy(weapons = weapons.map { weapon -> weapon.toUiModel() }) }
            }
        }
    }


    private fun checkIfPermissionAlreadyGranted(page: OnboardingPage) {
        val permission = when (page) {
            OnboardingPage.CAMERA -> AppPermission.CAMERA
            OnboardingPage.LOCATION -> AppPermission.LOCATION
            else -> return
        }
        viewModelScope.launch {
            if (checkPermissionStatusUseCase(permission) == PermissionStatus.GRANTED) {
                advancePage()
            }
        }
    }

    private fun complete() {
        val state = _uiModel.value
        viewModelScope.launch {
            completeOnboardingUseCase(
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
