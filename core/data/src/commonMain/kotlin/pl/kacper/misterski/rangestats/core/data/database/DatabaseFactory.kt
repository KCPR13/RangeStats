package pl.kacper.misterski.rangestats.core.data.database

import androidx.room.RoomDatabase

const val DATABASE_NAME = "rangstats.db"

expect fun getDatabaseBuilder(): RoomDatabase.Builder<AppDatabase>
