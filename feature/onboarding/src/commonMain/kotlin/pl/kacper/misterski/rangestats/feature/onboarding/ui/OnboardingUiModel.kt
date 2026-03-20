package pl.kacper.misterski.rangestats.feature.onboarding.ui

import pl.kacper.misterski.rangestats.core.domain.enums.UnitSystem
data class OnboardingUiModel(
    val currentPage: OnboardingPage = OnboardingPage.WELCOME,
    val displayName: String = "",
    val unitSystem: UnitSystem = UnitSystem.METRIC,
    val defaultDistanceMeters: Int = 25,
    val isCompleted: Boolean = false,
    val showBackButton: Boolean = false,
    val distanceLabel: String = "25 m",
    val minDistance: Int = 5,
    val maxDistance: Int = 1000,
) {
    companion object {
        val PAGE_COUNT = OnboardingPage.entries.size
    }
}
