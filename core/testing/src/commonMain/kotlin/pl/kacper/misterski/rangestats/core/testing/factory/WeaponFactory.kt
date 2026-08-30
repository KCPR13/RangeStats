package pl.kacper.misterski.rangestats.core.testing.factory

import pl.kacper.misterski.rangestats.core.domain.enums.Caliber
import pl.kacper.misterski.rangestats.core.domain.enums.WeaponType
import pl.kacper.misterski.rangestats.core.domain.models.Ammunition
import pl.kacper.misterski.rangestats.core.domain.models.Weapon

fun testWeapon(
    name: String = "Test Pistol",
    type: WeaponType = WeaponType.PISTOL,
    ammunition: Ammunition = Ammunition.CaliberAmmo(Caliber.LUGER_9MM),
): Weapon =
    Weapon(
        name = name,
        type = type,
        ammunition = ammunition,
    )
