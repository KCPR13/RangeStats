package pl.kacper.misterski.rangestats.feature.settings.domain.usecase

import pl.kacper.misterski.rangestats.core.domain.models.UserProfile
import pl.kacper.misterski.rangestats.feature.settings.domain.repository.ProfileRepository

class GetUserProfileUseCase(private val repo: ProfileRepository) {
    suspend operator fun invoke(): Result<UserProfile> = runCatching { repo.getUserProfile() }
}
