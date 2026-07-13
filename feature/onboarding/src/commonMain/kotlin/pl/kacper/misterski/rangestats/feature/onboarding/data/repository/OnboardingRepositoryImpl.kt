package pl.kacper.misterski.rangestats.feature.onboarding.data.repository

import pl.kacper.misterski.rangestats.core.data.datasource.userprefs.UserPrefsDataSource
import pl.kacper.misterski.rangestats.core.domain.models.UserProfile
import pl.kacper.misterski.rangestats.feature.onboarding.domain.repository.OnboardingRepository

class OnboardingRepositoryImpl(
    private val userPrefsDataSource: UserPrefsDataSource,
) : OnboardingRepository {

    override suspend fun isOnboardingCompleted(): Boolean =
        userPrefsDataSource.isOnboardingCompleted()

    override suspend fun completeOnboarding(profile: UserProfile) {
        userPrefsDataSource.updateUserProfile(profile)
        userPrefsDataSource.setOnboardingCompleted()
    }
}
