package pl.kacper.misterski.rangestats.data.database.core

import androidx.room.RenameColumn
import androidx.room.migration.AutoMigrationSpec

@RenameColumn(tableName = "settings", fromColumnName = "isDarkMode", toColumnName = "isDarkModes")
class Migration1To2Spec : AutoMigrationSpec