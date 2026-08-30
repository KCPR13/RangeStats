package pl.kacper.misterski.rangestats.core.domain.usecase

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import pl.kacper.misterski.rangestats.core.domain.models.Weapon
import pl.kacper.misterski.rangestats.core.domain.repository.WeaponRepository

class GetWeaponsUseCase(
    private val weaponRepository: WeaponRepository,
    private val ioDispatcher: CoroutineDispatcher,
) {
    operator fun invoke(): Flow<List<Weapon>> =
        flow {
            emit(weaponRepository.getAllWeapons())
        }.flowOn(ioDispatcher)
}
