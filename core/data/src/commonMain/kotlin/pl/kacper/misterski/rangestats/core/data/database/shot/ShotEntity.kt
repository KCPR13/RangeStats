package pl.kacper.misterski.rangestats.core.data.database.shot

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = ShotEntity.TABLE_NAME)
data class ShotEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val sessionId: Long,
    val zoneHit: String,
    val timestamp: Long,
) {
    companion object {
        const val TABLE_NAME = "shots"
    }
}