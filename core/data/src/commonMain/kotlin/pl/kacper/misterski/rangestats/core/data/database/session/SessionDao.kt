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
    suspend fun insertSession(entity: SessionEntity): Long

    @Update
    suspend fun updateSession(entity: SessionEntity)

    @Query("SELECT * FROM ${SessionEntity.TABLE_NAME} WHERE id = :id")
    suspend fun getSessionById(id: Long): SessionEntity?

    @Query("SELECT * FROM ${SessionEntity.TABLE_NAME} ORDER BY startedAt DESC")
    suspend fun getAllSessions(): List<SessionEntity>

    @Query("DELETE FROM ${SessionEntity.TABLE_NAME} WHERE id = :id")
    suspend fun deleteSession(id: Long)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertShot(entity: ShotEntity): Long

}
