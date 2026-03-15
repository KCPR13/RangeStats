package pl.kacper.misterski.rangestats.core.testing.factory

import pl.kacper.misterski.rangestats.core.domain.enums.WeaponType
import pl.kacper.misterski.rangestats.core.domain.models.Weapon

fun testWeapon(
    id: String = "test-weapon-1",
    name: String = "Test Pistol",
    type: WeaponType = WeaponType.PISTOL,
    caliber: String = "9mm",
    notes: String? = null,
): Weapon = Weapon(
    id = id,
    name = name,
    type = type,
    caliber = caliber,
    notes = notes,
)
