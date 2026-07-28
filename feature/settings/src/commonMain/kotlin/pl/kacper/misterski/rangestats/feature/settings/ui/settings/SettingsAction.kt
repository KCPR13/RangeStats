package pl.kacper.misterski.rangestats.feature.settings.ui.settings

import pl.kacper.misterski.rangestats.core.domain.enums.UnitSystem

sealed class SettingsAction {
    data object OnStart : SettingsAction()
    data object IncrementDistance : SettingsAction()
    data object DecrementDistance : SettingsAction()
    data object ShowDistanceEditDialog : SettingsAction()
    data object HideDistanceEditDialog : SettingsAction()
    data class DistanceInputChanged(val text: String) : SettingsAction()
    data object ConfirmDistanceInput : SettingsAction()
    data class UnitSystemChanged(val units: UnitSystem) : SettingsAction()
    data class DeleteWeapon(val name: String) : SettingsAction()
}
