package pl.kacper.misterski.rangestats.data.database.entities.statistics

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface StatisticsDao {
    @Query("SELECT * FROM statistics")
    fun getStatistics(): Flow<List<StatisticsEntity>>

    @Insert
    suspend fun insert(statistic: StatisticsEntity)
}
