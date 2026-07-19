package pl.kacper.misterski.rangestats.feature.settings.domain.usecase

import pl.kacper.misterski.rangestats.core.domain.enums.WeaponType
import pl.kacper.misterski.rangestats.core.domain.exceptions.InvalidAmmunitionException
import pl.kacper.misterski.rangestats.core.domain.models.Ammunition
import pl.kacper.misterski.rangestats.core.domain.models.Weapon
import pl.kacper.misterski.rangestats.feature.settings.domain.repository.WeaponRepository

class AddWeaponUseCase(private val weaponRepository: WeaponRepository) {
    suspend operator fun invoke(name: String, weaponType: WeaponType, ammunition: Ammunition): Result<Unit> =
        runCatching {
            if (!ammunition.isApplicableTo(weaponType)) throw InvalidAmmunitionException(weaponType)
            val weapon = Weapon(
                name = name,
                type = weaponType,
                ammunition = ammunition,
            )
            weaponRepository.addWeapon(weapon)
        }
}