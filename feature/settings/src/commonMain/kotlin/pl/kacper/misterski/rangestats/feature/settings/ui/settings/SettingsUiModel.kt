package pl.kacper.misterski.rangestats.feature.settings.ui.settings

import pl.kacper.misterski.rangestats.core.domain.enums.UnitSystem
import pl.kacper.misterski.rangestats.core.domain.models.UserProfile
import pl.kacper.misterski.rangestats.core.ui.component.WeaponIconUiModel

data class SettingsUiModel(
    val profile: UserProfile = UserProfile("", UnitSystem.METRIC, 25),
    val weapons: List<WeaponUiModel> = emptyList(),
    val isLoading: Boolean = false,
    val displayNameAbbreviation: String = ""
) {
    data class WeaponUiModel(
        val name: String,
        val badgeText: String,
        val icon: WeaponIconUiModel,
        val caliber: String,
    )
}
