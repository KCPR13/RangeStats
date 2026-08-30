package pl.kacper.misterski.rangestats.core.data.database.weapon

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = WeaponEntity.TABLE_NAME)
data class WeaponEntity(
    @PrimaryKey
    val name: String,
    val type: String,
    val caliber: String?,
    val gauge: String?,
) {
    companion object {
        const val TABLE_NAME = "weapons"
    }
}
