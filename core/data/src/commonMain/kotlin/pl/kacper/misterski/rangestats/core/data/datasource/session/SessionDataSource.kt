package pl.kacper.misterski.rangestats.core.data.datasource.session

import pl.kacper.misterski.rangestats.core.data.database.session.SessionEntity
import pl.kacper.misterski.rangestats.core.data.database.shot.ShotEntity

interface SessionDataSource {
    suspend fun insertSession(entity: SessionEntity)
    suspend fun updateSession(entity: SessionEntity)
    suspend fun getSessionById(id: String): SessionEntity?
    suspend fun getAllSessions(): List<SessionEntity>
    suspend fun deleteSession(id: String)
    suspend fun insertShot(entity: ShotEntity)
    suspend fun getShotsForSession(sessionId: String): List<ShotEntity>
}