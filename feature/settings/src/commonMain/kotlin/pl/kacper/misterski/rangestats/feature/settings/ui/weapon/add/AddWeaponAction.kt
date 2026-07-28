package pl.kacper.misterski.rangestats.feature.settings.ui.weapon.add

import pl.kacper.misterski.rangestats.core.domain.enums.WeaponType

sealed class AddWeaponAction {
    data object OnStart : AddWeaponAction()
    data class NameChanged(val name: String) : AddWeaponAction()
    data class TypeSelected(val type: WeaponType) : AddWeaponAction()
    data class AmmoSelected(val option: AddWeaponUiModel.AmmoOptionUiModel) : AddWeaponAction()
    data object Save : AddWeaponAction()
    data object Reset : AddWeaponAction()
}
