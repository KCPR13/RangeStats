package pl.kacper.misterski.rangestats.core.domain.repository

import pl.kacper.misterski.rangestats.core.domain.models.Weapon

interface WeaponRepository {
    suspend fun getAllWeapons(): List<Weapon>

    suspend fun getWeaponByName(name: String): Weapon?

    suspend fun addWeapon(weapon: Weapon)

    suspend fun deleteWeapon(name: String)
}
