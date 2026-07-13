package pl.kacper.misterski.rangestats.feature.settings.data.repository

import pl.kacper.misterski.rangestats.core.data.datasource.weapon.WeaponDataSource
import pl.kacper.misterski.rangestats.core.data.mapper.toDomain
import pl.kacper.misterski.rangestats.core.data.mapper.toEntity
import pl.kacper.misterski.rangestats.core.domain.models.Weapon
import pl.kacper.misterski.rangestats.feature.settings.domain.repository.WeaponRepository

class WeaponRepositoryImpl(
    private val weaponDataSource: WeaponDataSource,
) : WeaponRepository {

    override suspend fun getAllWeapons(): List<Weapon> =
        weaponDataSource.getAllWeapons().map { it.toDomain() }

    override suspend fun addWeapon(weapon: Weapon) {
        weaponDataSource.insertWeapon(weapon.toEntity())
    }

    override suspend fun deleteWeapon(name: String) {
        weaponDataSource.deleteWeapon(name)
    }
}
