package pl.kacper.misterski.rangestats.core.data.database.migrationspec

import androidx.room.DeleteColumn
import androidx.room.migration.AutoMigrationSpec

@DeleteColumn(tableName = "weapons", columnName = "notes")
class DeleteWeaponNotes : AutoMigrationSpec
