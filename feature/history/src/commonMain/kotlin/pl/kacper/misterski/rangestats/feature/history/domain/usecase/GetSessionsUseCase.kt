package pl.kacper.misterski.rangestats.feature.history.domain.usecase

import pl.kacper.misterski.rangestats.core.domain.models.Session
import pl.kacper.misterski.rangestats.feature.history.domain.repository.HistoryRepository

class GetSessionsUseCase(private val repository: HistoryRepository) {
    suspend operator fun invoke(): Result<List<Session>> = runCatching {
        repository.getSessions()
    }
}
