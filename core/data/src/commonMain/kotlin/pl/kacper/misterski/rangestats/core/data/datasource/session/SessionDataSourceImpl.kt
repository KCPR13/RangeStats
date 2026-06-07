package pl.kacper.misterski.rangestats.core.data.datasource.session

import pl.kacper.misterski.rangestats.core.data.database.session.SessionDao
import pl.kacper.misterski.rangestats.core.data.database.session.SessionEntity
import pl.kacper.misterski.rangestats.core.data.database.shot.ShotDao
import pl.kacper.misterski.rangestats.core.data.database.shot.ShotEntity

class SessionDataSourceImpl(
    private val sessionDao: SessionDao,
    private val shotDao: ShotDao
) : SessionDataSource {

    override suspend fun insertSession(entity: SessionEntity) {
        sessionDao.insertSession(entity)
    }

    override suspend fun updateSession(entity: SessionEntity) {
        sessionDao.updateSession(entity)
    }

    override suspend fun getSessionById(id: String): SessionEntity? =
        sessionDao.getSessionById(id)

    override suspend fun getAllSessions(): List<SessionEntity> =
        sessionDao.getAllSessions()

    override suspend fun deleteSession(id: String) {
        sessionDao.deleteSession(id)
    }

    override suspend fun insertShot(entity: ShotEntity) {
        sessionDao.insertShot(entity)
    }

    override suspend fun getShotsForSession(sessionId: String): List<ShotEntity> =
        shotDao.getShotsForSession(sessionId)
}