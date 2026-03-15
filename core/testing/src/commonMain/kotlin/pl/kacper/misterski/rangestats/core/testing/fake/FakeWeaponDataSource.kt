package pl.kacper.misterski.rangestats.core.testing.fake

import pl.kacper.misterski.rangestats.core.data.database.weapon.WeaponEntity
import pl.kacper.misterski.rangestats.core.data.datasource.weapon.WeaponDataSource

class FakeWeaponDataSource : WeaponDataSource {

    val weapons: MutableList<WeaponEntity> = mutableListOf()

    override suspend fun getAllWeapons(): List<WeaponEntity> = weapons.toList()

    override suspend fun getWeaponById(id: String): WeaponEntity? =
        weapons.firstOrNull { it.id == id }

    override suspend fun insertWeapon(entity: WeaponEntity) {
        weapons.removeAll { it.id == entity.id }
        weapons.add(entity)
    }

    override suspend fun updateWeapon(entity: WeaponEntity) {
        val index = weapons.indexOfFirst { it.id == entity.id }
        if (index >= 0) weapons[index] = entity
    }

    override suspend fun deleteWeapon(id: String) {
        weapons.removeAll { it.id == id }
    }
}
