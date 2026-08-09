package pl.kacper.misterski.rangestats.feature.settings.ui.settings

import org.jetbrains.compose.resources.StringResource
import pl.kacper.misterski.rangestats.core.domain.Constants.DEFAULT_DISTANCE_METERS
import pl.kacper.misterski.rangestats.core.domain.Constants.SESSION_DISTANCE_MAX
import pl.kacper.misterski.rangestats.core.domain.Constants.SESSION_DISTANCE_MIN
import pl.kacper.misterski.rangestats.core.domain.enums.UnitSystem
import pl.kacper.misterski.rangestats.core.domain.models.UserProfile
import pl.kacper.misterski.rangestats.core.ui.Res
import pl.kacper.misterski.rangestats.core.ui.component.WeaponIconUiModel
import pl.kacper.misterski.rangestats.core.ui.component.toggle.TacSegmentedToggleOptionUiModel
import pl.kacper.misterski.rangestats.core.ui.core_unit_suffix_meters

data class SettingsUiModel(
    val profile: UserProfile = UserProfile("", UnitSystem.METRIC, 25),
    val weapons: List<WeaponUiModel> = emptyList(),
    val isLoading: Boolean = false,
    val distanceEdit: DistanceEditUiModel = DistanceEditUiModel(),
    val distanceDisplay: DistanceDisplayUiModel = DistanceDisplayUiModel(),
    val unitOptions: List<TacSegmentedToggleOptionUiModel> = emptyList(),
) {
    data class WeaponUiModel(
        val name: String,
        val icon: WeaponIconUiModel,
        val ammoLabel: String,
    )

    data class DistanceEditUiModel(
        val isVisible: Boolean = false,
        val inputText: String = "",
        val isInputValid: Boolean = true,
    )

    data class DistanceDisplayUiModel(
        val value: Int = DEFAULT_DISTANCE_METERS,
        val min: Int = SESSION_DISTANCE_MIN,
        val max: Int = SESSION_DISTANCE_MAX,
        val unitSuffix: StringResource = Res.string.core_unit_suffix_meters,
    )
}
