package pl.kacper.misterski.rangestats.data.database.entities.settings

import androidx.room.Entity
import androidx.room.PrimaryKey
import pl.kacper.misterski.rangestats.data.database.entities.settings.SettingsEntity.Companion.TABLE_NAME

@Entity(tableName = TABLE_NAME)
data class SettingsEntity(
    @PrimaryKey
    val id: Int = 1,
    val isDarkMode: Boolean
) {
    companion object {
        const val TABLE_NAME = "settings"
    }
}
