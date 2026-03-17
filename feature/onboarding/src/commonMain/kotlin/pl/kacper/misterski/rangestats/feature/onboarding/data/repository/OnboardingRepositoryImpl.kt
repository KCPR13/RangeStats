package pl.kacper.misterski.rangestats.feature.onboarding.data.repository

import pl.kacper.misterski.rangestats.core.data.datasource.userprefs.UserPrefsDataSource
import pl.kacper.misterski.rangestats.core.domain.models.UserProfile
import pl.kacper.misterski.rangestats.feature.onboarding.domain.repository.OnboardingRepository

class OnboardingRepositoryImpl(
    private val userPrefs: UserPrefsDataSource,
) : OnboardingRepository {

    override suspend fun isOnboardingCompleted(): Boolean =
        userPrefs.isOnboardingCompleted()

    override suspend fun completeOnboarding(profile: UserProfile) {
        userPrefs.updateUserProfile(profile)
        userPrefs.setOnboardingCompleted()
    }
}
