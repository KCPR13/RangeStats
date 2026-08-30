package pl.kacper.misterski.rangestats.core.data.datasource.weapon

import pl.kacper.misterski.rangestats.core.data.database.weapon.WeaponDao
import pl.kacper.misterski.rangestats.core.data.database.weapon.WeaponEntity

class WeaponDataSourceImpl(
    private val weaponDao: WeaponDao,
) : WeaponDataSource {
    override suspend fun getAllWeapons(): List<WeaponEntity> = weaponDao.getAllWeapons()

    override suspend fun getWeaponByName(name: String): WeaponEntity? = weaponDao.getWeaponByName(name)

    override suspend fun insertWeapon(entity: WeaponEntity) {
        weaponDao.insertWeapon(entity)
    }

    override suspend fun updateWeapon(entity: WeaponEntity) {
        weaponDao.updateWeapon(entity)
    }

    override suspend fun deleteWeapon(name: String) {
        weaponDao.deleteWeapon(name)
    }
}
