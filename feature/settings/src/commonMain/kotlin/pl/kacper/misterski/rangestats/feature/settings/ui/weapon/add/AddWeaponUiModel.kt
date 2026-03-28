package pl.kacper.misterski.rangestats.feature.settings.ui.weapon.add

import pl.kacper.misterski.rangestats.core.domain.enums.WeaponType

//TODO hardcoded
data class AddWeaponUiModel(
    val name: String = "",
    val selectedType: WeaponType = WeaponType.PISTOL,
    val selectedCaliber: String = "9mm Para",
    val isSaving: Boolean = false,
    val isSaved: Boolean = false,
)
