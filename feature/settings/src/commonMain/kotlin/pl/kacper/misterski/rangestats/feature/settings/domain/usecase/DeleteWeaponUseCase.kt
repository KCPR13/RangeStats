package pl.kacper.misterski.rangestats.feature.settings.domain.usecase

import pl.kacper.misterski.rangestats.feature.settings.domain.repository.WeaponRepository

class DeleteWeaponUseCase(private val weaponRepository: WeaponRepository) {
    suspend operator fun invoke(name: String): Result<Unit> = runCatching { weaponRepository.deleteWeapon(name) }
}
