package pl.kacper.misterski.rangestats.feature.settings.data.repository

import pl.kacper.misterski.rangestats.core.data.datasource.userprefs.UserPrefsDataSource
import pl.kacper.misterski.rangestats.core.domain.models.UserProfile
import pl.kacper.misterski.rangestats.feature.settings.domain.repository.ProfileRepository

class ProfileRepositoryImpl(
    private val userPrefsDataSource: UserPrefsDataSource,
) : ProfileRepository {

    override suspend fun getUserProfile(): UserProfile = userPrefsDataSource.getUserProfile()

    override suspend fun updateUserProfile(profile: UserProfile) {
        userPrefsDataSource.updateUserProfile(profile)
    }
}
