package pl.kacper.misterski.rangestats.feature.session.domain.usecase

import pl.kacper.misterski.rangestats.core.domain.models.Shot
import pl.kacper.misterski.rangestats.feature.session.domain.repository.SessionRepository
//TODO needed?
class AddShotUseCase(private val repository: SessionRepository) {
    suspend operator fun invoke(shot: Shot): Result<Unit> = runCatching {
        repository.addShot(shot)
    }
}
