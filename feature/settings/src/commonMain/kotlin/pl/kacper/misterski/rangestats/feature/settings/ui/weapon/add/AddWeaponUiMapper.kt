package pl.kacper.misterski.rangestats.feature.settings.ui.weapon.add

import org.jetbrains.compose.resources.StringResource
import org.jetbrains.compose.resources.getString
import pl.kacper.misterski.rangestats.core.domain.enums.Caliber
import pl.kacper.misterski.rangestats.core.domain.enums.ShotgunGauge
import pl.kacper.misterski.rangestats.core.domain.enums.WeaponType
import rangestats.feature.settings.generated.resources.Res
import rangestats.feature.settings.generated.resources.add_weapon_caliber_label
import rangestats.feature.settings.generated.resources.add_weapon_gauge_label
import rangestats.feature.settings.generated.resources.weapon_badge_pistol
import rangestats.feature.settings.generated.resources.weapon_badge_revolver
import rangestats.feature.settings.generated.resources.weapon_badge_rifle
import rangestats.feature.settings.generated.resources.weapon_badge_shotgun

suspend fun weaponTypeOptionsUiModel(selected: WeaponType): List<AddWeaponUiModel.WeaponTypeOptionUiModel> =
    WeaponType.entries.map { type ->
        AddWeaponUiModel.WeaponTypeOptionUiModel(
            type = type,
            label = type.toBadgeLabel(),
            selected = type == selected,
        )
    }

private suspend fun WeaponType.toBadgeLabel(): String = when (this) {
    WeaponType.PISTOL -> getString(Res.string.weapon_badge_pistol)
    WeaponType.REVOLVER -> getString(Res.string.weapon_badge_revolver)
    WeaponType.SHOTGUN -> getString(Res.string.weapon_badge_shotgun)
    WeaponType.RIFLE -> getString(Res.string.weapon_badge_rifle)
}

fun WeaponType.toAmmoOptionUiModels() =
    if (this == WeaponType.SHOTGUN) {
        ShotgunGauge.entries.mapIndexed { index, gauge ->
            AddWeaponUiModel.AmmoOptionUiModel(
                key = gauge.name,
                label = gauge.label,
                selected = index == 0
            )
        }
    } else {
        Caliber.entries.filter { this in it.applicableTo }.mapIndexed { index, caliber ->
            AddWeaponUiModel.AmmoOptionUiModel(
                key = caliber.name,
                label = caliber.label,
                selected = index == 0
            )
        }
    }

fun WeaponType.toAmmoLabelRes(): StringResource =
    if (this == WeaponType.SHOTGUN) Res.string.add_weapon_gauge_label else Res.string.add_weapon_caliber_label
