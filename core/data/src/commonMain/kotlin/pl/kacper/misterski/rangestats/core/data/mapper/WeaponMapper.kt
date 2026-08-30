package pl.kacper.misterski.rangestats.core.data.mapper

import pl.kacper.misterski.rangestats.core.data.database.weapon.WeaponEntity
import pl.kacper.misterski.rangestats.core.domain.enums.Caliber
import pl.kacper.misterski.rangestats.core.domain.enums.ShotgunGauge
import pl.kacper.misterski.rangestats.core.domain.enums.WeaponType
import pl.kacper.misterski.rangestats.core.domain.exceptions.CorruptedWeaponAmmunitionException
import pl.kacper.misterski.rangestats.core.domain.models.Ammunition
import pl.kacper.misterski.rangestats.core.domain.models.Weapon

fun WeaponEntity.toDomain(): Weapon =
    Weapon(
        name = name,
        type = WeaponType.entries.find { it.name == type } ?: WeaponType.PISTOL,
        ammunition = toAmmunition(),
    )

private fun WeaponEntity.toAmmunition(): Ammunition {
    caliber?.let { name -> Caliber.entries.find { it.name == name }?.let { return Ammunition.CaliberAmmo(it) } }
    gauge?.let { name -> ShotgunGauge.entries.find { it.name == name }?.let { return Ammunition.GaugeAmmo(it) } }
    throw CorruptedWeaponAmmunitionException(name)
}

fun Weapon.toEntity(): WeaponEntity {
    val ammo = ammunition
    val (caliberName, gaugeName) =
        when (ammo) {
            is Ammunition.CaliberAmmo -> ammo.caliber.name to null
            is Ammunition.GaugeAmmo -> null to ammo.gauge.name
        }
    return WeaponEntity(
        name = name,
        type = type.name,
        caliber = caliberName,
        gauge = gaugeName,
    )
}
