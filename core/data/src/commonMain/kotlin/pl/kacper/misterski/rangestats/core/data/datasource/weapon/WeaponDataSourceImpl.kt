package pl.kacper.misterski.rangestats.core.data.datasource.weapon

import pl.kacper.misterski.rangestats.core.data.database.weapon.WeaponDao
import pl.kacper.misterski.rangestats.core.data.database.weapon.WeaponEntity

class WeaponDataSourceImpl(private val dao: WeaponDao) : WeaponDataSource {

    override suspend fun getAllWeapons(): List<WeaponEntity> =
        dao.getAllWeapons()

    override suspend fun getWeaponByName(name: String): WeaponEntity? =
        dao.getWeaponByName(name)

    override suspend fun insertWeapon(entity: WeaponEntity) {
        dao.insertWeapon(entity)
    }

    override suspend fun updateWeapon(entity: WeaponEntity) {
        dao.updateWeapon(entity)
    }

    override suspend fun deleteWeapon(name: String) {
        dao.deleteWeapon(name)
    }
}