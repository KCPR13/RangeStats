package pl.kacper.misterski.rangestats.data.database.core

import androidx.room.AutoMigration
import androidx.room.ConstructedBy
import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.RoomDatabaseConstructor
import pl.kacper.misterski.rangestats.data.database.entities.settings.SettingsDao
import pl.kacper.misterski.rangestats.data.database.entities.settings.SettingsEntity
import pl.kacper.misterski.rangestats.data.database.entities.statistics.StatisticsDao
import pl.kacper.misterski.rangestats.data.database.entities.statistics.StatisticsEntity

const val DATABASE_NAME = "app_database.db"


@Database(
    version = 2,
    entities = [SettingsEntity::class,
        StatisticsEntity::class],
    autoMigrations = [AutoMigration(from = 1, to = 2)]
    )
@ConstructedBy(AppDatabaseConstructor::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun getSettingsDao(): SettingsDao
    abstract fun getStatisticsDao(): StatisticsDao
}

@Suppress("KotlinNoActualForExpect")
expect object AppDatabaseConstructor : RoomDatabaseConstructor<AppDatabase> {
    override fun initialize(): AppDatabase
}