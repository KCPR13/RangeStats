package pl.kacper.misterski.rangestats.feature.onboarding.ui

import pl.kacper.misterski.rangestats.core.domain.enums.UnitSystem

sealed class OnboardingAction {
    data object NextPage : OnboardingAction()
    data object PreviousPage : OnboardingAction()
    data object Skip : OnboardingAction()
    data object Complete : OnboardingAction()
    data class UpdateDisplayName(val name: String) : OnboardingAction()
    data class SelectUnitSystem(val system: UnitSystem) : OnboardingAction()
    data object IncrementDistance : OnboardingAction()
    data object DecrementDistance : OnboardingAction()
}
