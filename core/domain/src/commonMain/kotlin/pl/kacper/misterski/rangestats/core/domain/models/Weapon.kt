package pl.kacper.misterski.rangestats.core.domain.models

import pl.kacper.misterski.rangestats.core.domain.enums.WeaponType

data class Weapon(
    val name: String,
    val type: WeaponType,
    val caliber: String,
)
