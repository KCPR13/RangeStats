package pl.kacper.misterski.rangestats.feature.session.domain.usecase

import pl.kacper.misterski.rangestats.core.domain.models.Weapon
import pl.kacper.misterski.rangestats.feature.session.domain.repository.WeaponLookupRepository

class GetWeaponByNameUseCase(private val weaponLookupRepository: WeaponLookupRepository) {
    suspend operator fun invoke(name: String): Result<Weapon?> = runCatching {
        weaponLookupRepository.getWeapon(name)
    }
}