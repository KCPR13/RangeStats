package pl.kacper.misterski.rangestats.core.data.mapper

import pl.kacper.misterski.rangestats.core.data.database.weapon.WeaponEntity
import pl.kacper.misterski.rangestats.core.domain.enums.WeaponType
import pl.kacper.misterski.rangestats.core.domain.models.Weapon

fun WeaponEntity.toDomain(): Weapon = Weapon(
    name = name,
    type = WeaponType.entries.find { it.name == type } ?: WeaponType.PISTOL,
    caliber = caliber,
)

fun Weapon.toEntity(): WeaponEntity = WeaponEntity(
    name = name,
    type = type.name,
    caliber = caliber,
)
