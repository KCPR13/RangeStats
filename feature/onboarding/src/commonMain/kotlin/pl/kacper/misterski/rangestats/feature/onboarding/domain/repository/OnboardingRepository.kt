package pl.kacper.misterski.rangestats.feature.onboarding.domain.repository

import pl.kacper.misterski.rangestats.core.domain.models.UserProfile

interface OnboardingRepository {
    suspend fun isOnboardingCompleted(): Boolean
    suspend fun completeOnboarding(profile: UserProfile)
}
