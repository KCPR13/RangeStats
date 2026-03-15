package pl.kacper.misterski.rangestats.core.testing.fake

import pl.kacper.misterski.rangestats.core.data.database.session.SessionEntity
import pl.kacper.misterski.rangestats.core.data.database.shot.ShotEntity
import pl.kacper.misterski.rangestats.core.data.datasource.session.SessionDataSource

class FakeSessionDataSource : SessionDataSource {

    val sessions: MutableList<SessionEntity> = mutableListOf()
    val shots: MutableList<ShotEntity> = mutableListOf()

    override suspend fun insertSession(entity: SessionEntity) {
        sessions.removeAll { it.id == entity.id }
        sessions.add(entity)
    }

    override suspend fun updateSession(entity: SessionEntity) {
        val index = sessions.indexOfFirst { it.id == entity.id }
        if (index >= 0) sessions[index] = entity
    }

    override suspend fun getSessionById(id: String): SessionEntity? =
        sessions.firstOrNull { it.id == id }

    override suspend fun getAllSessions(): List<SessionEntity> = sessions.toList()

    override suspend fun deleteSession(id: String) {
        sessions.removeAll { it.id == id }
        shots.removeAll { it.sessionId == id }
    }

    override suspend fun insertShot(entity: ShotEntity) {
        shots.add(entity)
    }

    override suspend fun getShotsForSession(sessionId: String): List<ShotEntity> =
        shots.filter { it.sessionId == sessionId }
}
