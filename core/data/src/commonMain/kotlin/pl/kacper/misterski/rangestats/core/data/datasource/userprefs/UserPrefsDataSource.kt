package pl.kacper.misterski.rangestats.core.data.datasource.userprefs

import pl.kacper.misterski.rangestats.core.domain.models.UserProfile

interface UserPrefsDataSource {
    @Throws(Exception::class)
    suspend fun getUserProfile(): UserProfile
    suspend fun updateUserProfile(profile: UserProfile)
}