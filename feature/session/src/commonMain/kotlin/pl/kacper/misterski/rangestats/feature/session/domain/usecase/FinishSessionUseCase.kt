package pl.kacper.misterski.rangestats.feature.session.domain.usecase

import pl.kacper.misterski.rangestats.core.domain.models.Session
import pl.kacper.misterski.rangestats.feature.session.domain.repository.SessionRepository

class FinishSessionUseCase(private val sessionRepository: SessionRepository) {
    suspend operator fun invoke(sessionId: Long, score: Float?): Result<Session> =
        sessionRepository.finishSession(sessionId, score)
}
