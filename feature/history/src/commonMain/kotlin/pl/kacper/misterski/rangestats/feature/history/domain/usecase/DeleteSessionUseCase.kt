package pl.kacper.misterski.rangestats.feature.history.domain.usecase

import pl.kacper.misterski.rangestats.feature.history.domain.repository.HistoryRepository

class DeleteSessionUseCase(private val repository: HistoryRepository) {
    suspend operator fun invoke(sessionId: String): Result<Unit> = runCatching {
        repository.deleteSession(sessionId)
    }
}
