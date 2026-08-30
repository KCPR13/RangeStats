package pl.kacper.misterski.rangestats.core.domain.exceptions

import pl.kacper.misterski.rangestats.core.domain.enums.WeaponType

class InvalidAmmunitionException(
    weaponType: WeaponType,
) : IllegalArgumentException("Ammunition is not applicable to weapon type: $weaponType")
