package pl.kacper.misterski.rangestats.feature.session.domain.usecase

import pl.kacper.misterski.rangestats.core.domain.exceptions.SessionNotFoundException
import pl.kacper.misterski.rangestats.core.domain.models.Session
import pl.kacper.misterski.rangestats.feature.session.domain.repository.SessionRepository

class GetSessionUseCase(private val repository: SessionRepository) {
    suspend operator fun invoke(sessionId: String): Result<Session> = runCatching {
        repository.getSession(sessionId) ?: throw SessionNotFoundException(sessionId)
    }
}
