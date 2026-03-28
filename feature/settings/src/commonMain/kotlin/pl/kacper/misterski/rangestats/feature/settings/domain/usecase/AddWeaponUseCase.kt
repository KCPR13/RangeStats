package pl.kacper.misterski.rangestats.feature.settings.domain.usecase

import kotlin.random.Random
import pl.kacper.misterski.rangestats.core.domain.enums.WeaponType
import pl.kacper.misterski.rangestats.core.domain.models.Weapon
import pl.kacper.misterski.rangestats.feature.settings.domain.repository.WeaponRepository

class AddWeaponUseCase(private val repo: WeaponRepository) {
    suspend operator fun invoke(name: String, type: WeaponType, caliber: String): Result<Unit> =
        runCatching {
            val weapon = Weapon(
                id = Random.nextLong(Long.MAX_VALUE).toString(16), // TODO very bad
                name = name,
                type = type,
                caliber = caliber,
                notes = null,
            )
            repo.addWeapon(weapon)
        }
}
