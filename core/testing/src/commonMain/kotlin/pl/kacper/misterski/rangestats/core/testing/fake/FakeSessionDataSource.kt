package pl.kacper.misterski.rangestats.core.testing.fake

import pl.kacper.misterski.rangestats.core.data.database.session.SessionEntity
import pl.kacper.misterski.rangestats.core.data.database.shot.ShotEntity
import pl.kacper.misterski.rangestats.core.data.datasource.session.SessionDataSource

class FakeSessionDataSource : SessionDataSource {

    val sessions: MutableList<SessionEntity> = mutableListOf()
    val shots: MutableList<ShotEntity> = mutableListOf()

    override suspend fun insertSession(entity: SessionEntity): Long {
        val id = if (entity.id != 0L) entity.id else (sessions.maxOfOrNull { it.id } ?: 0) + 1
        sessions.removeAll { it.id == id }
        sessions.add(entity.copy(id = id))
        return id
    }

    override suspend fun updateSession(entity: SessionEntity) {
        val index = sessions.indexOfFirst { it.id == entity.id }
        if (index >= 0) sessions[index] = entity
    }

    override suspend fun getSessionById(id: Long): SessionEntity? =
        sessions.firstOrNull { it.id == id }

    override suspend fun getAllSessions(): List<SessionEntity> = sessions.toList()

    override suspend fun deleteSession(id: Long) {
        sessions.removeAll { it.id == id }
        shots.removeAll { it.sessionId == id }
    }

    override suspend fun insertShot(entity: ShotEntity): Long {
        val id = if (entity.id != 0L) entity.id else (shots.maxOfOrNull { it.id } ?: 0) + 1
        shots.add(entity.copy(id = id))
        return id
    }

    override suspend fun getShotsForSession(sessionId: Long): List<ShotEntity> =
        shots.filter { it.sessionId == sessionId }
}
