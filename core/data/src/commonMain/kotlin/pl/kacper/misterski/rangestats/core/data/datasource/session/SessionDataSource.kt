package pl.kacper.misterski.rangestats.core.data.datasource.session

import pl.kacper.misterski.rangestats.core.data.database.session.SessionEntity
import pl.kacper.misterski.rangestats.core.data.database.shot.ShotEntity

interface SessionDataSource {
    suspend fun insertSession(entity: SessionEntity): Long

    suspend fun updateSession(entity: SessionEntity)

    suspend fun getSessionById(id: Long): SessionEntity?

    suspend fun getAllSessions(): List<SessionEntity>

    suspend fun deleteSession(id: Long)

    suspend fun insertShots(entities: List<ShotEntity>)

    suspend fun getShotsForSession(sessionId: Long): List<ShotEntity>
}
