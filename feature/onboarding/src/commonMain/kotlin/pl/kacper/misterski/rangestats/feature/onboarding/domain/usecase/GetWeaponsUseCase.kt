package pl.kacper.misterski.rangestats.feature.onboarding.domain.usecase

import pl.kacper.misterski.rangestats.core.domain.models.Weapon
import pl.kacper.misterski.rangestats.feature.onboarding.domain.repository.ArsenalRepository

class GetWeaponsUseCase(private val arsenalRepository: ArsenalRepository) {
    suspend operator fun invoke(): Result<List<Weapon>> = runCatching {
        arsenalRepository.getWeapons()
    }
}
