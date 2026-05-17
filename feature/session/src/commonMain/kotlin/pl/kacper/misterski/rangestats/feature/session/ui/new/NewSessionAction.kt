package pl.kacper.misterski.rangestats.feature.session.ui.new

import pl.kacper.misterski.rangestats.core.domain.enums.TargetType

sealed class NewSessionAction {
    data class LocationChanged(val name: String) : NewSessionAction()
    data class WeaponSelected(val weaponId: String) : NewSessionAction()
    data object IncrementDistance : NewSessionAction()
    data object DecrementDistance : NewSessionAction()
    data class TargetTypeSelected(val type: TargetType) : NewSessionAction()
    data object StartSession : NewSessionAction()
    data object Back : NewSessionAction()
}
