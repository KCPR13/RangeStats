package pl.kacper.misterski.rangestats.feature.session.data.repository

import pl.kacper.misterski.rangestats.core.data.datasource.weapon.WeaponDataSource
import pl.kacper.misterski.rangestats.core.data.mapper.toDomain
import pl.kacper.misterski.rangestats.core.domain.models.Weapon
import pl.kacper.misterski.rangestats.feature.session.domain.repository.WeaponLookupRepository

class WeaponLookupRepositoryImpl(
    private val weaponDataSource: WeaponDataSource,
) : WeaponLookupRepository {

    override suspend fun getWeapon(name: String): Weapon? =
        weaponDataSource.getWeaponByName(name)?.toDomain()
}