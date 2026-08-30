package pl.kacper.misterski.rangestats.core.testing.fake

import pl.kacper.misterski.rangestats.core.domain.models.Weapon
import pl.kacper.misterski.rangestats.core.domain.repository.WeaponRepository

class FakeWeaponRepository : WeaponRepository {
    val weapons = mutableListOf<Weapon>()

    override suspend fun getAllWeapons(): List<Weapon> = weapons

    override suspend fun getWeaponByName(name: String): Weapon? = weapons.firstOrNull { it.name == name }

    override suspend fun addWeapon(weapon: Weapon) {
        weapons += weapon
    }

    override suspend fun deleteWeapon(name: String) {
        weapons.removeAll { it.name == name }
    }
}
