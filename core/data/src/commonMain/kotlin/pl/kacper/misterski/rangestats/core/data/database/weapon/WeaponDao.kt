package pl.kacper.misterski.rangestats.core.data.database.weapon

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update

@Dao
interface WeaponDao {
    @Query("SELECT * FROM ${WeaponEntity.TABLE_NAME} ORDER BY name ASC")
    suspend fun getAllWeapons(): List<WeaponEntity>

    @Query("SELECT * FROM ${WeaponEntity.TABLE_NAME} WHERE name = :name")
    suspend fun getWeaponByName(name: String): WeaponEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertWeapon(entity: WeaponEntity)

    @Update
    suspend fun updateWeapon(entity: WeaponEntity)

    @Query("DELETE FROM ${WeaponEntity.TABLE_NAME} WHERE name = :name")
    suspend fun deleteWeapon(name: String)
}
