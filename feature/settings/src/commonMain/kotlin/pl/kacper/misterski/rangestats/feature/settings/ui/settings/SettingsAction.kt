package pl.kacper.misterski.rangestats.feature.settings.ui.settings

import pl.kacper.misterski.rangestats.core.domain.enums.UnitSystem

sealed class SettingsAction {
    data object IncrementDistance : SettingsAction()
    data object DecrementDistance : SettingsAction()
    data class UnitSystemChanged(val units: UnitSystem) : SettingsAction()
}
