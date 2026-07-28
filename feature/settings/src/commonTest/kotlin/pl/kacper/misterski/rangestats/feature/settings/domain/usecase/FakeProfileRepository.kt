package pl.kacper.misterski.rangestats.feature.settings.domain.usecase

import pl.kacper.misterski.rangestats.core.domain.enums.UnitSystem
import pl.kacper.misterski.rangestats.core.domain.models.UserProfile
import pl.kacper.misterski.rangestats.feature.settings.domain.repository.ProfileRepository

class FakeProfileRepository : ProfileRepository {
    var profile: UserProfile = UserProfile(
        displayName = "Test User",
        units = UnitSystem.METRIC,
        defaultDistanceMeters = 25,
    )

    override suspend fun getUserProfile(): UserProfile = profile

    override suspend fun updateUserProfile(profile: UserProfile) {
        this.profile = profile
    }
}