package pl.kacper.misterski.rangestats.core.domain.exceptions

class CorruptedWeaponAmmunitionException(
    weaponName: String,
) : IllegalStateException("Weapon '$weaponName' has neither a valid caliber nor a valid gauge")
