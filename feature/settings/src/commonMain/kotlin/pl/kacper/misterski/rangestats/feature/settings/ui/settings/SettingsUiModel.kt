package pl.kacper.misterski.rangestats.feature.settings.ui.settings

import pl.kacper.misterski.rangestats.core.domain.enums.UnitSystem
import pl.kacper.misterski.rangestats.core.domain.enums.WeaponType
import pl.kacper.misterski.rangestats.core.domain.models.UserProfile

data class SettingsUiModel(
    val profile: UserProfile = UserProfile("", UnitSystem.METRIC, 25),
    val weapons: List<WeaponUiModel> = emptyList(),
    val isLoading: Boolean = false,
    val displayNameAbbreviation: String = ""
) {
    data class WeaponUiModel(
        val name: String,
        val badgeText: String,
        val type: WeaponType,
        val caliber: String,
    )
}
