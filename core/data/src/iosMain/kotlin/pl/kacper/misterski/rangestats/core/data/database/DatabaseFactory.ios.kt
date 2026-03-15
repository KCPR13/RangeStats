package pl.kacper.misterski.rangestats.core.data.database

import androidx.room.Room
import androidx.room.RoomDatabase
import kotlinx.cinterop.ExperimentalForeignApi
import platform.Foundation.NSHomeDirectory

@OptIn(ExperimentalForeignApi::class)
actual fun getDatabaseBuilder(): RoomDatabase.Builder<AppDatabase> {
    val dbPath = NSHomeDirectory() + "/${DATABASE_NAME}"
    return Room.databaseBuilder<AppDatabase>(name = dbPath)
}
