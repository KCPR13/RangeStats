package pl.kacper.misterski.rangestats.feature.history.data.repository

import pl.kacper.misterski.rangestats.core.data.datasource.session.SessionDataSource
import pl.kacper.misterski.rangestats.core.data.mapper.toDomain
import pl.kacper.misterski.rangestats.core.domain.models.Session
import pl.kacper.misterski.rangestats.feature.history.domain.repository.HistoryRepository

class HistoryRepositoryImpl(
    private val sessionDataSource: SessionDataSource,
) : HistoryRepository {

    override suspend fun getSessions(): List<Session> {
        val entities = sessionDataSource.getAllSessions()
        return entities.map { entity ->
            val shots = sessionDataSource.getShotsForSession(entity.id)
            entity.toDomain(shots)
        }
    }

    override suspend fun getSession(id: Long): Session? {
        val entity = sessionDataSource.getSessionById(id) ?: return null
        val shots = sessionDataSource.getShotsForSession(id)
        return entity.toDomain(shots)
    }

    override suspend fun deleteSession(id: Long) {
        sessionDataSource.deleteSession(id)
    }
}
