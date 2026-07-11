package pl.kacper.misterski.rangestats.feature.onboarding.data.repository

import pl.kacper.misterski.rangestats.core.data.datasource.weapon.WeaponDataSource
import pl.kacper.misterski.rangestats.core.data.mapper.toDomain
import pl.kacper.misterski.rangestats.core.domain.models.Weapon
import pl.kacper.misterski.rangestats.feature.onboarding.domain.repository.ArsenalRepository

class ArsenalRepositoryImpl(
    private val weaponDataSource: WeaponDataSource,
) : ArsenalRepository {
    override suspend fun getWeapons(): List<Weapon> =
        weaponDataSource.getAllWeapons().map { it.toDomain() }
}
