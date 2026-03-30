package pl.kacper.misterski.rangestats.feature.settings.ui.weapon

sealed class WeaponListAction {
    data class DeleteWeapon(val id: String) : WeaponListAction()
    data object OnStart : WeaponListAction()
}
