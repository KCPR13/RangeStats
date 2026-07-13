package pl.kacper.misterski.rangestats.feature.settings.domain.usecase

import pl.kacper.misterski.rangestats.core.domain.models.UserProfile
import pl.kacper.misterski.rangestats.feature.settings.domain.repository.ProfileRepository

class UpdateUserProfileUseCase(private val profileRepository: ProfileRepository) {
    suspend operator fun invoke(profile: UserProfile): Result<Unit> =
        runCatching { profileRepository.updateUserProfile(profile) }
}
