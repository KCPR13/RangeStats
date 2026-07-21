package pl.kacper.misterski.rangestats.feature.settings.domain.usecase

import pl.kacper.misterski.rangestats.core.domain.models.Weapon
import pl.kacper.misterski.rangestats.feature.settings.domain.repository.WeaponRepository

class FakeWeaponRepository : WeaponRepository {
    val weapons = mutableListOf<Weapon>()
    override suspend fun getAllWeapons(): List<Weapon> = weapons
    override suspend fun addWeapon(weapon: Weapon) {
        weapons += weapon
    }
    override suspend fun deleteWeapon(name: String) {
        weapons.removeAll { it.name == name }
    }
}