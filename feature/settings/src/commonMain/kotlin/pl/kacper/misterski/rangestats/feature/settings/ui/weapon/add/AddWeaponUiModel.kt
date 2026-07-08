package pl.kacper.misterski.rangestats.feature.settings.ui.weapon.add

import pl.kacper.misterski.rangestats.core.domain.enums.WeaponType

data class AddWeaponUiModel(
    val name: String = "",
    val selectedType: WeaponType = WeaponType.PISTOL,
    val isSaving: Boolean = false,
    val isSaved: Boolean = false,
    val calibers: List<CaliberUiModel> = defaultCaliberUiModels(),
) {
    data class CaliberUiModel(val name: String, val selected: Boolean = false)
}
