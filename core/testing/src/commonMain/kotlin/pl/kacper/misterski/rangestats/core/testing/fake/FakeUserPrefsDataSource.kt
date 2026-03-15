package pl.kacper.misterski.rangestats.core.testing.fake

import pl.kacper.misterski.rangestats.core.data.datasource.userprefs.UserPrefsDataSource
import pl.kacper.misterski.rangestats.core.domain.enums.UnitSystem
import pl.kacper.misterski.rangestats.core.domain.models.UserProfile

class FakeUserPrefsDataSource : UserPrefsDataSource {

    var profile: UserProfile = UserProfile(
        id = "test-user",
        displayName = "Test User",
        units = UnitSystem.METRIC,
        defaultDistanceMeters = 25,
    )

    override suspend fun getUserProfile(): UserProfile = profile

    override suspend fun updateUserProfile(profile: UserProfile) {
        this.profile = profile
    }
}
