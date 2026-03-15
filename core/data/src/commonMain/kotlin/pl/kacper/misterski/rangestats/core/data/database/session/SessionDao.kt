package pl.kacper.misterski.rangestats.core.data.database.session

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import pl.kacper.misterski.rangestats.core.data.database.shot.ShotEntity

@Dao
interface SessionDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertSession(entity: SessionEntity)

    @Update
    suspend fun updateSession(entity: SessionEntity)

    @Query("SELECT * FROM sessions WHERE id = :id")
    suspend fun getSessionById(id: String): SessionEntity?

    @Query("SELECT * FROM sessions ORDER BY startedAt DESC")
    suspend fun getAllSessions(): List<SessionEntity>

    @Query("DELETE FROM sessions WHERE id = :id")
    suspend fun deleteSession(id: String)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertShot(entity: ShotEntity)

    @Query("SELECT * FROM shots WHERE sessionId = :sessionId ORDER BY timestamp ASC")
    suspend fun getShotsForSession(sessionId: String): List<ShotEntity>
}
