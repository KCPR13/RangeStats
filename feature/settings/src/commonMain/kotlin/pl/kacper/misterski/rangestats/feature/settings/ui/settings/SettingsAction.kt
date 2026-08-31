package pl.kacper.misterski.rangestats.feature.settings.ui.settings

sealed class SettingsAction {
    data object OnStart : SettingsAction()
    data object IncrementDistance : SettingsAction()
    data object DecrementDistance : SettingsAction()
    data object ShowDistanceEditDialog : SettingsAction()
    data object HideDistanceEditDialog : SettingsAction()
    data class DistanceInputChanged(val text: String) : SettingsAction()
    data object ConfirmDistanceInput : SettingsAction()
    data class UnitSystemChanged(val index: Int) : SettingsAction()
    data class DeleteWeapon(val name: String) : SettingsAction()
    data object AddWeapon : SettingsAction()
}
