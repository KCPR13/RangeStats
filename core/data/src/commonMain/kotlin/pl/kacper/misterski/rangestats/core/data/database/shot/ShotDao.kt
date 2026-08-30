package pl.kacper.misterski.rangestats.core.data.database.shot

import androidx.room.Dao
import androidx.room.Query

@Dao
interface ShotDao {
    @Query("SELECT * FROM ${ShotEntity.TABLE_NAME} WHERE sessionId = :sessionId ORDER BY timestamp ASC")
    suspend fun getShotsForSession(sessionId: Long): List<ShotEntity>
}
