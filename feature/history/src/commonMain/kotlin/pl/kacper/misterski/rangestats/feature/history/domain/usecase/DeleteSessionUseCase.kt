package pl.kacper.misterski.rangestats.feature.history.domain.usecase

import pl.kacper.misterski.rangestats.feature.history.domain.repository.HistoryRepository

class DeleteSessionUseCase(private val historyRepository: HistoryRepository) {
    suspend operator fun invoke(sessionId: Long): Result<Unit> = runCatching {
        historyRepository.deleteSession(sessionId)
    }
}
