package pl.kacper.misterski.rangestats.core.data.database.weapon

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update

@Dao
interface WeaponDao {

    @Query("SELECT * FROM weapons ORDER BY name ASC")
    suspend fun getAllWeapons(): List<WeaponEntity>

    @Query("SELECT * FROM weapons WHERE id = :id")
    suspend fun getWeaponById(id: String): WeaponEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertWeapon(entity: WeaponEntity)

    @Update
    suspend fun updateWeapon(entity: WeaponEntity)

    @Query("DELETE FROM weapons WHERE id = :id")
    suspend fun deleteWeapon(id: String)
}
