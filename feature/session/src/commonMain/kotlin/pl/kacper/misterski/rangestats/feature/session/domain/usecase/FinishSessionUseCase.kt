package pl.kacper.misterski.rangestats.feature.session.domain.usecase

import pl.kacper.misterski.rangestats.core.domain.models.Session
import pl.kacper.misterski.rangestats.feature.session.domain.repository.SessionRepository

class FinishSessionUseCase(private val repository: SessionRepository) {
    suspend operator fun invoke(sessionId: Long): Result<Session> =
        repository.finishSession(sessionId)
}
