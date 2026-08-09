package pl.kacper.misterski.rangestats.feature.settings.ui.settings

import org.jetbrains.compose.resources.StringResource
import org.jetbrains.compose.resources.getString
import pl.kacper.misterski.rangestats.core.domain.Constants
import pl.kacper.misterski.rangestats.core.domain.converter.UnitConverter
import pl.kacper.misterski.rangestats.core.domain.enums.UnitSystem
import pl.kacper.misterski.rangestats.core.domain.models.UserProfile
import pl.kacper.misterski.rangestats.core.domain.models.Weapon
import pl.kacper.misterski.rangestats.core.ui.component.toDistanceUnitSuffixRes
import pl.kacper.misterski.rangestats.core.ui.component.toUiModel
import pl.kacper.misterski.rangestats.core.ui.component.toggle.TacSegmentedToggleOptionUiModel
import rangestats.feature.settings.generated.resources.Res
import rangestats.feature.settings.generated.resources.settings_imperial
import rangestats.feature.settings.generated.resources.settings_metric

fun UserProfile.toDistanceDisplayUiModel(
    unitConverter: UnitConverter,
): SettingsUiModel.DistanceDisplayUiModel = SettingsUiModel.DistanceDisplayUiModel(
    value = unitConverter.toDisplay(defaultDistanceMeters, units),
    min = unitConverter.toDisplay(Constants.SESSION_DISTANCE_MIN, units),
    max = unitConverter.toDisplay(Constants.SESSION_DISTANCE_MAX, units),
    unitSuffix = units.toDistanceUnitSuffixRes(),
)

suspend fun unitOptionsUiModel(selected: UnitSystem): List<TacSegmentedToggleOptionUiModel> =
    UnitSystem.entries.map { unit ->
        TacSegmentedToggleOptionUiModel(label = getString(unit.toLabelRes()), selected = unit == selected)
    }

private fun UnitSystem.toLabelRes(): StringResource = when (this) {
    UnitSystem.METRIC -> Res.string.settings_metric
    UnitSystem.IMPERIAL -> Res.string.settings_imperial
}

suspend fun Weapon.toUiModel(): SettingsUiModel.WeaponUiModel {
    return SettingsUiModel.WeaponUiModel(
        name = this.name,
        icon = this.type.toUiModel(),
        ammoLabel = this.ammunition.displayLabel,
    )
}