package pl.kacper.misterski.rangestats.feature.ballistics.domain.usecase

import pl.kacper.misterski.rangestats.feature.ballistics.domain.model.CaliberPreset
import pl.kacper.misterski.rangestats.feature.ballistics.domain.repository.BallisticsRepository

class GetCaliberPresetsUseCase(private val repository: BallisticsRepository) {
    operator fun invoke(): List<CaliberPreset> = repository.getCaliberPresets()
}
