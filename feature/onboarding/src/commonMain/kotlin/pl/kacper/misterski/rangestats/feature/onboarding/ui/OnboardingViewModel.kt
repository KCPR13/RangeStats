package pl.kacper.misterski.rangestats.feature.onboarding.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.jetbrains.compose.resources.getString
import pl.kacper.misterski.rangestats.core.domain.models.UserProfile
import pl.kacper.misterski.rangestats.feature.onboarding.domain.usecase.CompleteOnboardingUseCase
import rangestats.feature.onboarding.generated.resources.Res
import rangestats.feature.onboarding.generated.resources.onboarding_placeholder_distance

//TODO hardcoded
class OnboardingViewModel(
    private val completeOnboarding: CompleteOnboardingUseCase,
) : ViewModel() {

    private val _uiModel = MutableStateFlow(OnboardingUiModel())
    val uiModel: StateFlow<OnboardingUiModel> = _uiModel.asStateFlow()

    fun onAction(action: OnboardingAction) {
        when (action) {
            is OnboardingAction.NextPage -> advancePage()
            is OnboardingAction.PreviousPage -> goBack()
            is OnboardingAction.Skip -> complete()
            is OnboardingAction.Complete -> complete()
            is OnboardingAction.UpdateDisplayName -> _uiModel.update { it.copy(displayName = action.name) }
            is OnboardingAction.SelectUnitSystem -> _uiModel.update { it.copy(unitSystem = action.system) }
            OnboardingAction.DecrementDistance -> decrementDistance()
            OnboardingAction.IncrementDistance -> incrementDistance()
        }
    }

    private fun incrementDistance() {
        val newDistance = _uiModel.value.defaultDistanceMeters.plus(DISTANCE_STEP_IN_METERS)
        viewModelScope.launch {
            _uiModel.update {
                it.copy(defaultDistanceMeters = newDistance,
                    distanceLabel = getString(Res.string.onboarding_placeholder_distance, newDistance))
            }
        }
    }

    private fun decrementDistance() {
        val newDistance = _uiModel.value.defaultDistanceMeters.minus(DISTANCE_STEP_IN_METERS)
        viewModelScope.launch {
            _uiModel.update {
                it.copy(defaultDistanceMeters = newDistance,
                    distanceLabel = getString(Res.string.onboarding_placeholder_distance, newDistance))
            }
        }
    }

    private fun advancePage() {
        val entries = OnboardingPage.entries
        val current = _uiModel.value.currentPage
        val nextIndex = current.ordinal.plus(1)

        if (nextIndex < entries.size) {
            val newPage = entries[nextIndex]
            val showBackButton = newPage != OnboardingPage.WELCOME
            _uiModel.update { it.copy(currentPage = newPage, showBackButton = showBackButton) }
        } else {
            complete()
        }
    }

    private fun goBack() {
        val entries = OnboardingPage.entries
        val current = _uiModel.value.currentPage
        val prevIndex = current.ordinal.minus(1)
        if (prevIndex >= 0) {
            val newPage = entries[prevIndex]
            val showBackButton = newPage != OnboardingPage.WELCOME
            _uiModel.update {
                it.copy(
                    currentPage = entries[prevIndex],
                    showBackButton = showBackButton
                )
            }
        }
    }

    private fun complete() {
        val state = _uiModel.value
        viewModelScope.launch {
            completeOnboarding(
                UserProfile(
                    id = "default",
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
