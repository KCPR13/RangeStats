package pl.kacper.misterski.rangestats.feature.onboarding.ui

import pl.kacper.misterski.rangestats.core.domain.enums.UnitSystem
// TODO app icon fix
data class OnboardingUiModel(
    val currentPage: OnboardingPage = OnboardingPage.WELCOME,
    val displayName: String = "",
    val unitSystem: UnitSystem = UnitSystem.METRIC,
    val defaultDistanceMeters: Int = 25,
    val isCompleted: Boolean = false,
    val showBackButton: Boolean = false
) {
    companion object {
        val PAGE_COUNT = OnboardingPage.entries.size
    }
}
