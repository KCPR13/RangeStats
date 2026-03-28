package pl.kacper.misterski.rangestats.feature.settings.ui.settings

import pl.kacper.misterski.rangestats.core.domain.enums.UnitSystem
import pl.kacper.misterski.rangestats.core.domain.models.UserProfile
import pl.kacper.misterski.rangestats.core.domain.models.Weapon

//TODO K hardcoded
data class SettingsUiModel(
    val profile: UserProfile = UserProfile("Operator", UnitSystem.METRIC, 25),
    val weapons: List<Weapon> = emptyList(),
    val isLoading: Boolean = false,
)
