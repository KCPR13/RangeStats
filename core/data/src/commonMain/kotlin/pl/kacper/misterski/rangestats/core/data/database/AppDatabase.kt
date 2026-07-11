package pl.kacper.misterski.rangestats.core.data.database

import androidx.room.AutoMigration
import androidx.room.ConstructedBy
import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.RoomDatabaseConstructor
import pl.kacper.misterski.rangestats.core.data.database.migration_spec.DeleteWeaponNotes
import pl.kacper.misterski.rangestats.core.data.database.session.SessionDao
import pl.kacper.misterski.rangestats.core.data.database.session.SessionEntity
import pl.kacper.misterski.rangestats.core.data.database.shot.ShotDao
import pl.kacper.misterski.rangestats.core.data.database.shot.ShotEntity
import pl.kacper.misterski.rangestats.core.data.database.weapon.WeaponDao
import pl.kacper.misterski.rangestats.core.data.database.weapon.WeaponEntity

@Database(
    entities = [SessionEntity::class, ShotEntity::class, WeaponEntity::class],
    version = 3,
    exportSchema = true,
    autoMigrations = [
        AutoMigration(from = 1, to = 2, spec = DeleteWeaponNotes::class),
    ],
)
@ConstructedBy(AppDatabaseConstructor::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun sessionDao(): SessionDao
    abstract fun weaponDao(): WeaponDao
    abstract fun shotDao(): ShotDao
}

@Suppress("NO_ACTUAL_FOR_EXPECT")
expect object AppDatabaseConstructor : RoomDatabaseConstructor<AppDatabase> {
    override fun initialize(): AppDatabase
}
