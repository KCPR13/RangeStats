package pl.kacper.misterski.rangestats.feature.settings.domain.usecase

import pl.kacper.misterski.rangestats.core.domain.enums.WeaponType
import pl.kacper.misterski.rangestats.core.domain.models.Weapon
import pl.kacper.misterski.rangestats.feature.settings.domain.repository.WeaponRepository

class AddWeaponUseCase(private val weaponRepository: WeaponRepository) {
    suspend operator fun invoke(name: String, type: WeaponType, caliber: String): Result<Unit> =
        runCatching {
            val weapon = Weapon(
                name = name,
                type = type,
                caliber = caliber,
            )
            weaponRepository.addWeapon(weapon)
        }
}
