package pl.kacper.misterski.rangestats.feature.onboarding.domain.repository

import pl.kacper.misterski.rangestats.core.domain.models.Weapon

interface ArsenalRepository {
    suspend fun getWeapons(): List<Weapon>
}
