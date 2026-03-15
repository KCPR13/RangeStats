package pl.kacper.misterski.rangestats.core.data.mapper

import pl.kacper.misterski.rangestats.core.data.database.weapon.WeaponEntity
import pl.kacper.misterski.rangestats.core.domain.enums.WeaponType
import pl.kacper.misterski.rangestats.core.domain.models.Weapon

fun WeaponEntity.toDomain(): Weapon = Weapon(
    id = id,
    name = name,
    type = WeaponType.valueOf(type),
    caliber = caliber,
    notes = notes,
)

fun Weapon.toEntity(): WeaponEntity = WeaponEntity(
    id = id,
    name = name,
    type = type.name,
    caliber = caliber,
    notes = notes,
)
