package pl.kacper.misterski.rangestats.feature.settings.data.repository

import pl.kacper.misterski.rangestats.core.data.datasource.userprefs.UserPrefsDataSource
import pl.kacper.misterski.rangestats.core.domain.models.UserProfile
import pl.kacper.misterski.rangestats.feature.settings.domain.repository.ProfileRepository

class ProfileRepositoryImpl(
    private val dataSource: UserPrefsDataSource,
) : ProfileRepository {

    override suspend fun getUserProfile(): UserProfile = dataSource.getUserProfile()

    override suspend fun updateUserProfile(profile: UserProfile) {
        dataSource.updateUserProfile(profile)
    }
}
