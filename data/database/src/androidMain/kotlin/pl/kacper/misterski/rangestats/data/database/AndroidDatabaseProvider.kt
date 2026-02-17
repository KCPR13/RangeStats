package pl.kacper.misterski.rangestats.data.database

import android.content.Context
import androidx.room.Room
import androidx.room.RoomDatabase
import pl.kacper.misterski.rangestats.data.database.core.AppDatabase
import pl.kacper.misterski.rangestats.data.database.core.DATABASE_NAME


fun getDatabase(context: Context): RoomDatabase.Builder<AppDatabase> {
    val appContext = context.applicationContext
    val dbFile = appContext.getDatabasePath(DATABASE_NAME)
    return Room.databaseBuilder<AppDatabase>(
        context = appContext,
        name = dbFile.absolutePath
    )
}