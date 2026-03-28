package pl.kacper.misterski.rangestats.feature.settings.data.repository

import pl.kacper.misterski.rangestats.core.data.datasource.weapon.WeaponDataSource
import pl.kacper.misterski.rangestats.core.data.mapper.toDomain
import pl.kacper.misterski.rangestats.core.data.mapper.toEntity
import pl.kacper.misterski.rangestats.core.domain.models.Weapon
import pl.kacper.misterski.rangestats.feature.settings.domain.repository.WeaponRepository

class WeaponRepositoryImpl(
    private val dataSource: WeaponDataSource,
) : WeaponRepository {

    override suspend fun getAllWeapons(): List<Weapon> =
        dataSource.getAllWeapons().map { it.toDomain() }

    override suspend fun addWeapon(weapon: Weapon) {
        dataSource.insertWeapon(weapon.toEntity())
    }

    override suspend fun deleteWeapon(id: String) {
        dataSource.deleteWeapon(id)
    }
}
