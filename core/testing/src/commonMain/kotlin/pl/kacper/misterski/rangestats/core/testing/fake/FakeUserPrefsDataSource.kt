package pl.kacper.misterski.rangestats.core.testing.fake

import pl.kacper.misterski.rangestats.core.data.datasource.userprefs.UserPrefsDataSource
import pl.kacper.misterski.rangestats.core.domain.enums.UnitSystem
import pl.kacper.misterski.rangestats.core.domain.models.UserProfile

class FakeUserPrefsDataSource : UserPrefsDataSource {
    var profile: UserProfile =
        UserProfile(
            displayName = "Test User",
            units = UnitSystem.METRIC,
            defaultDistanceMeters = 25,
        )

    var onboardingCompleted: Boolean = false

    override suspend fun getUserProfile(): UserProfile = profile

    override suspend fun updateUserProfile(profile: UserProfile) {
        this.profile = profile
    }

    override suspend fun isOnboardingCompleted(): Boolean = onboardingCompleted

    override suspend fun setOnboardingCompleted() {
        onboardingCompleted = true
    }
}
