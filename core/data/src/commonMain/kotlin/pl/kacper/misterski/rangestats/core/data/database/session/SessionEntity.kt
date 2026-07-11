package pl.kacper.misterski.rangestats.core.data.database.session

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = SessionEntity.TABLE_NAME)
data class SessionEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val weaponId: String,
    val locationName: String,
    val distanceMeters: Int,
    val targetType: String,
    val startedAt: Long,
    val finishedAt: Long?,
    val score: Float?,
) {
    companion object {
        const val TABLE_NAME = "sessions"
    }
}
