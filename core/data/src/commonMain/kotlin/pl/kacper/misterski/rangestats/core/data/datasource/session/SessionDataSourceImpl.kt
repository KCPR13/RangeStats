package pl.kacper.misterski.rangestats.core.data.datasource.session

import pl.kacper.misterski.rangestats.core.data.database.session.SessionDao
import pl.kacper.misterski.rangestats.core.data.database.session.SessionEntity
import pl.kacper.misterski.rangestats.core.data.database.shot.ShotEntity

class SessionDataSourceImpl(private val dao: SessionDao) : SessionDataSource {

    override suspend fun insertSession(entity: SessionEntity) {
        dao.insertSession(entity)
    }

    override suspend fun updateSession(entity: SessionEntity) {
        dao.updateSession(entity)
    }

    override suspend fun getSessionById(id: String): SessionEntity? =
        dao.getSessionById(id)

    override suspend fun getAllSessions(): List<SessionEntity> =
        dao.getAllSessions()

    override suspend fun deleteSession(id: String) {
        dao.deleteSession(id)
    }

    override suspend fun insertShot(entity: ShotEntity) {
        dao.insertShot(entity)
    }

    override suspend fun getShotsForSession(sessionId: String): List<ShotEntity> =
        dao.getShotsForSession(sessionId)
}