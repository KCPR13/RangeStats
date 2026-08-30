package pl.kacper.misterski.rangestats.core.domain.usecase

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import pl.kacper.misterski.rangestats.core.domain.enums.WeaponType
import pl.kacper.misterski.rangestats.core.domain.exceptions.InvalidAmmunitionException
import pl.kacper.misterski.rangestats.core.domain.models.Ammunition
import pl.kacper.misterski.rangestats.core.domain.models.Weapon
import pl.kacper.misterski.rangestats.core.domain.repository.WeaponRepository

class AddWeaponUseCase(
    private val weaponRepository: WeaponRepository,
    private val ioDispatcher: CoroutineDispatcher,
) {
    operator fun invoke(
        name: String,
        weaponType: WeaponType,
        ammunition: Ammunition,
    ): Flow<Unit> =
        flow {
            validate(weaponType, ammunition)
            emit(
                weaponRepository.addWeapon(
                    Weapon(
                        name = name,
                        type = weaponType,
                        ammunition = ammunition,
                    ),
                ),
            )
        }.flowOn(ioDispatcher)

    private fun validate(
        weaponType: WeaponType,
        ammunition: Ammunition,
    ) {
        if (!ammunition.isApplicableTo(weaponType)) throw InvalidAmmunitionException(weaponType)
    }
}
