package pl.kacper.misterski.rangestats.feature.settings.ui.settings

import pl.kacper.misterski.rangestats.core.domain.Constants
import pl.kacper.misterski.rangestats.core.domain.converter.UnitConverter
import pl.kacper.misterski.rangestats.core.domain.models.UserProfile
import pl.kacper.misterski.rangestats.core.domain.models.Weapon
import pl.kacper.misterski.rangestats.core.ui.component.toDistanceUnitSuffixRes
import pl.kacper.misterski.rangestats.core.ui.component.toUiModel

fun UserProfile.toDistanceDisplayUiModel(
    unitConverter: UnitConverter,
): SettingsUiModel.DistanceDisplayUiModel = SettingsUiModel.DistanceDisplayUiModel(
    value = unitConverter.toDisplay(defaultDistanceMeters, units),
    min = unitConverter.toDisplay(Constants.SESSION_DISTANCE_MIN, units),
    max = unitConverter.toDisplay(Constants.SESSION_DISTANCE_MAX, units),
    unitSuffix = units.toDistanceUnitSuffixRes(),
)

suspend fun Weapon.toUiModel(): SettingsUiModel.WeaponUiModel {
    return SettingsUiModel.WeaponUiModel(
        name = this.name,
        icon = this.type.toUiModel(),
        ammoLabel = this.ammunition.displayLabel,
    )
}