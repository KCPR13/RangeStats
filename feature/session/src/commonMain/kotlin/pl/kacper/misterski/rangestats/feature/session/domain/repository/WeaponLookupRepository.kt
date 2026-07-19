package pl.kacper.misterski.rangestats.feature.session.domain.repository

import pl.kacper.misterski.rangestats.core.domain.models.Weapon

interface WeaponLookupRepository {
    suspend fun getWeapon(name: String): Weapon?
}