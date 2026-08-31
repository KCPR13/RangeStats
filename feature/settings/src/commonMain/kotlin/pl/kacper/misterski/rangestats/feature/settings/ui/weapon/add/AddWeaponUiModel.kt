package pl.kacper.misterski.rangestats.feature.settings.ui.weapon.add

import org.jetbrains.compose.resources.StringResource
import pl.kacper.misterski.rangestats.core.domain.enums.WeaponType

data class AddWeaponUiModel(
    val name: String = "",
    val selectedType: WeaponType = WeaponType.PISTOL,
    val isSaving: Boolean = false,
    val weaponTypeOptions: List<WeaponTypeOptionUiModel> = emptyList(),
    val ammoOptions: List<AmmoOptionUiModel> = WeaponType.PISTOL.toAmmoOptionUiModels(),
    val ammoLabelRes: StringResource = WeaponType.PISTOL.toAmmoLabelRes(),
) {
    data class AmmoOptionUiModel(val key: String, val label: String, val selected: Boolean = false)
    data class WeaponTypeOptionUiModel(val type: WeaponType, val label: String, val selected: Boolean = false)
}