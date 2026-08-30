package pl.kacper.misterski.rangestats.core.data.datasource.weapon

import pl.kacper.misterski.rangestats.core.data.database.weapon.WeaponEntity

interface WeaponDataSource {
    suspend fun getAllWeapons(): List<WeaponEntity>

    suspend fun getWeaponByName(name: String): WeaponEntity?

    suspend fun insertWeapon(entity: WeaponEntity)

    suspend fun updateWeapon(entity: WeaponEntity)

    suspend fun deleteWeapon(name: String)
}
