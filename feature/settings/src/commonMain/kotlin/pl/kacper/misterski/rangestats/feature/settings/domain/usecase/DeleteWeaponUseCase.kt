package pl.kacper.misterski.rangestats.feature.settings.domain.usecase

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import pl.kacper.misterski.rangestats.feature.settings.domain.repository.WeaponRepository

class DeleteWeaponUseCase(
    private val weaponRepository: WeaponRepository,
    private val ioDispatcher: CoroutineDispatcher,
) {
    operator fun invoke(name: String): Flow<Unit> = flow {
        emit(weaponRepository.deleteWeapon(name))
    }.flowOn(ioDispatcher)
}