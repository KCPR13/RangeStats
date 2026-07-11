package pl.kacper.misterski.rangestats.feature.history.domain.usecase

import pl.kacper.misterski.rangestats.core.domain.exceptions.SessionNotFoundException
import pl.kacper.misterski.rangestats.core.domain.models.Session
import pl.kacper.misterski.rangestats.feature.history.domain.repository.HistoryRepository

class GetSessionDetailUseCase(private val repository: HistoryRepository) {
    suspend operator fun invoke(sessionId: Long): Result<Session> = runCatching {
        repository.getSession(sessionId) ?: throw SessionNotFoundException(sessionId)
    }
}
