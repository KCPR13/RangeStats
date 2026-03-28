package pl.kacper.misterski.rangestats.feature.settings.domain.repository

import pl.kacper.misterski.rangestats.core.domain.models.UserProfile

interface ProfileRepository {
    suspend fun getUserProfile(): UserProfile
    suspend fun updateUserProfile(profile: UserProfile)
}
