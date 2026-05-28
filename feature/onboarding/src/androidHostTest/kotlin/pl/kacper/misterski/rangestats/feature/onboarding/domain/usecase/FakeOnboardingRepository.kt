package pl.kacper.misterski.rangestats.feature.onboarding.domain.usecase

import pl.kacper.misterski.rangestats.core.domain.models.UserProfile
import pl.kacper.misterski.rangestats.feature.onboarding.domain.repository.OnboardingRepository

class FakeOnboardingRepository : OnboardingRepository {
    var onboardingCompleted = false
    var lastSavedProfile: UserProfile? = null
    var completeCallCount = 0

    override suspend fun isOnboardingCompleted(): Boolean = onboardingCompleted

    override suspend fun completeOnboarding(profile: UserProfile) {
        lastSavedProfile = profile
        onboardingCompleted = true
        completeCallCount++
    }
}