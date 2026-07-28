package pl.kacper.misterski.rangestats.feature.settings.domain.usecase

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import pl.kacper.misterski.rangestats.core.domain.models.UserProfile
import pl.kacper.misterski.rangestats.feature.settings.domain.repository.ProfileRepository

class UpdateUserProfileUseCase(
    private val profileRepository: ProfileRepository,
    private val ioDispatcher: CoroutineDispatcher,
) {
    operator fun invoke(profile: UserProfile): Flow<Unit> = flow {
        emit(profileRepository.updateUserProfile(profile))
    }.flowOn(ioDispatcher)
}