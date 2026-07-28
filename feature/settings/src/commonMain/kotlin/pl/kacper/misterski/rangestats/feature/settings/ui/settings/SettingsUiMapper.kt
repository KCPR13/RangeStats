package pl.kacper.misterski.rangestats.feature.settings.ui.settings

import org.jetbrains.compose.resources.getString
import pl.kacper.misterski.rangestats.core.domain.Constants
import pl.kacper.misterski.rangestats.core.domain.converter.UnitConverter
import pl.kacper.misterski.rangestats.core.domain.enums.WeaponType
import pl.kacper.misterski.rangestats.core.domain.models.UserProfile
import pl.kacper.misterski.rangestats.core.domain.models.Weapon
import pl.kacper.misterski.rangestats.core.ui.component.toDistanceUnitSuffixRes
import pl.kacper.misterski.rangestats.core.ui.component.toUiModel
import rangestats.feature.settings.generated.resources.Res
import rangestats.feature.settings.generated.resources.weapon_badge_pistol
import rangestats.feature.settings.generated.resources.weapon_badge_revolver
import rangestats.feature.settings.generated.resources.weapon_badge_rifle
import rangestats.feature.settings.generated.resources.weapon_badge_shotgun

fun UserProfile.toDistanceDisplayUiModel(
    unitConverter: UnitConverter,
): SettingsUiModel.DistanceDisplayUiModel = SettingsUiModel.DistanceDisplayUiModel(
    value = unitConverter.toDisplay(defaultDistanceMeters, units),
    min = unitConverter.toDisplay(Constants.SESSION_DISTANCE_MIN, units),
    max = unitConverter.toDisplay(Constants.SESSION_DISTANCE_MAX, units),
    unitSuffix = units.toDistanceUnitSuffixRes(),
)

suspend fun Weapon.toUiModel(): SettingsUiModel.WeaponUiModel {
    val badgeText = when (this.type) {
        WeaponType.PISTOL -> getString(Res.string.weapon_badge_pistol)
        WeaponType.REVOLVER -> getString(Res.string.weapon_badge_revolver)
        WeaponType.SHOTGUN -> getString(Res.string.weapon_badge_shotgun)
        WeaponType.RIFLE -> getString(Res.string.weapon_badge_rifle)
    }

    return SettingsUiModel.WeaponUiModel(
        name = this.name,
        icon = this.type.toUiModel(),
        ammoLabel = this.ammunition.displayLabel,
        badgeText = badgeText
    )
}