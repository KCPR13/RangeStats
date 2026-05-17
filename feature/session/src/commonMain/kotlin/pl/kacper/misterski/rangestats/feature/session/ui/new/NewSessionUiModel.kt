package pl.kacper.misterski.rangestats.feature.session.ui.new

import pl.kacper.misterski.rangestats.core.domain.enums.TargetType
import pl.kacper.misterski.rangestats.core.domain.models.Weapon

data class NewSessionUiModel(
    val locationName: String = "",
    val weapons: List<Weapon> = emptyList(),
    val selectedWeaponName: String? = null,
    val distanceMeters: Int = 25,
    val targetType: TargetType = TargetType.ISSF_ROUND,
    val isLoading: Boolean = false,
    val canStart: Boolean = false,
    val navigateToActiveSession: String? = null,
)
