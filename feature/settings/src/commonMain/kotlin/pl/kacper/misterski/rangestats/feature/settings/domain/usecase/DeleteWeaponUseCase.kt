package pl.kacper.misterski.rangestats.feature.settings.domain.usecase

import pl.kacper.misterski.rangestats.feature.settings.domain.repository.WeaponRepository

class DeleteWeaponUseCase(private val repo: WeaponRepository) {
    suspend operator fun invoke(id: String): Result<Unit> = runCatching { repo.deleteWeapon(id) }
}
