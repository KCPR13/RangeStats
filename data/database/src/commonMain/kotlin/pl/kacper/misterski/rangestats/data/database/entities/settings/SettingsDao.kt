package pl.kacper.misterski.rangestats.data.database.entities.settings

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert

@Dao
interface SettingsDao {
    @Query("SELECT * FROM settings WHERE id = 1")
    suspend fun getSettings(): SettingsEntity?

    @Upsert
    suspend fun saveSettings(settings: SettingsEntity)
}
