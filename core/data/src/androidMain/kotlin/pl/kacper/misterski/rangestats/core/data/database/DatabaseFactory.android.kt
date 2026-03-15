package pl.kacper.misterski.rangestats.core.data.database

import android.content.Context
import androidx.room.Room
import androidx.room.RoomDatabase
import org.koin.mp.KoinPlatform

actual fun getDatabaseBuilder(): RoomDatabase.Builder<AppDatabase> {
    val context: Context = KoinPlatform.getKoin().get()
    return Room.databaseBuilder<AppDatabase>(
        context = context,
        name = DATABASE_NAME,
    )
}
