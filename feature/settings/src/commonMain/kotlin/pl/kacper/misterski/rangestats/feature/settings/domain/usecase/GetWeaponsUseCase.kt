package pl.kacper.misterski.rangestats.feature.settings.domain.usecase

import pl.kacper.misterski.rangestats.core.domain.models.Weapon
import pl.kacper.misterski.rangestats.feature.settings.domain.repository.WeaponRepository

class GetWeaponsUseCase(private val weaponRepository: WeaponRepository) {
    suspend operator fun invoke(): Result<List<Weapon>> = runCatching { weaponRepository.getAllWeapons() }
}
