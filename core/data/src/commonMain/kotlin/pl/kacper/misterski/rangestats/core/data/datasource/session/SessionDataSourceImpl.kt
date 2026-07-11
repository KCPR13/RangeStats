package pl.kacper.misterski.rangestats.core.data.datasource.session

import pl.kacper.misterski.rangestats.core.data.database.session.SessionDao
import pl.kacper.misterski.rangestats.core.data.database.session.SessionEntity
import pl.kacper.misterski.rangestats.core.data.database.shot.ShotDao
import pl.kacper.misterski.rangestats.core.data.database.shot.ShotEntity

class SessionDataSourceImpl(
    private val sessionDao: SessionDao,
    private val shotDao: ShotDao
) : SessionDataSource {

    override suspend fun insertSession(entity: SessionEntity): Long =
        sessionDao.insertSession(entity)

    override suspend fun updateSession(entity: SessionEntity) {
        sessionDao.updateSession(entity)
    }

    override suspend fun getSessionById(id: Long): SessionEntity? =
        sessionDao.getSessionById(id)

    override suspend fun getAllSessions(): List<SessionEntity> =
        sessionDao.getAllSessions()

    override suspend fun deleteSession(id: Long) {
        sessionDao.deleteSession(id)
    }

    override suspend fun insertShot(entity: ShotEntity): Long =
        sessionDao.insertShot(entity)

    override suspend fun getShotsForSession(sessionId: Long): List<ShotEntity> =
        shotDao.getShotsForSession(sessionId)
}