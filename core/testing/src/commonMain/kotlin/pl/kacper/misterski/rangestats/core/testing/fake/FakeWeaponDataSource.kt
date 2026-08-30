package pl.kacper.misterski.rangestats.core.testing.fake

import pl.kacper.misterski.rangestats.core.data.database.weapon.WeaponEntity
import pl.kacper.misterski.rangestats.core.data.datasource.weapon.WeaponDataSource

class FakeWeaponDataSource : WeaponDataSource {
    val weapons: MutableList<WeaponEntity> = mutableListOf()

    override suspend fun getAllWeapons(): List<WeaponEntity> = weapons.toList()

    override suspend fun getWeaponByName(name: String): WeaponEntity? = weapons.firstOrNull { it.name == name }

    override suspend fun insertWeapon(entity: WeaponEntity) {
        weapons.removeAll { it.name == entity.name }
        weapons.add(entity)
    }

    override suspend fun updateWeapon(entity: WeaponEntity) {
        val index = weapons.indexOfFirst { it.name == entity.name }
        if (index >= 0) weapons[index] = entity
    }

    override suspend fun deleteWeapon(name: String) {
        weapons.removeAll { it.name == name }
    }
}
