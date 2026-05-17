package pl.kacper.misterski.rangestats.feature.settings.ui.weapon.add

import pl.kacper.misterski.rangestats.core.domain.enums.WeaponType
import pl.kacper.misterski.rangestats.feature.settings.domain.enums.Caliber

data class AddWeaponUiModel(
    val name: String = "",
    val selectedType: WeaponType = WeaponType.PISTOL,
    val isSaving: Boolean = false,
    val isSaved: Boolean = false,
    val calibers: List<CaliberUiModel> = defaultCalibers,
) {
    data class CaliberUiModel(val name: String, val selected: Boolean = false)

    companion object { // TODO should be here?
        val defaultCalibers: List<CaliberUiModel> = Caliber.entries.mapIndexed { index, caliber ->
            CaliberUiModel(name = caliber.label, selected = index == 0)
        }
    }
}
