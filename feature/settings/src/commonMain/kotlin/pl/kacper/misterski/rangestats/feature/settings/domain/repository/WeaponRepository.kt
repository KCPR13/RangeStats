package pl.kacper.misterski.rangestats.feature.settings.domain.repository

import pl.kacper.misterski.rangestats.core.domain.models.Weapon

interface WeaponRepository {
    suspend fun getAllWeapons(): List<Weapon>
    suspend fun addWeapon(weapon: Weapon)
    suspend fun deleteWeapon(name: String)
}
