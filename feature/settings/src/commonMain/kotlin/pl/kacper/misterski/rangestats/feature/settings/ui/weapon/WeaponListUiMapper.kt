package pl.kacper.misterski.rangestats.feature.settings.ui.weapon

import pl.kacper.misterski.rangestats.core.domain.models.Weapon
import pl.kacper.misterski.rangestats.core.ui.component.toUiModel

fun Weapon.toUiModel(): WeaponListUiModel.WeaponRowUiModel = WeaponListUiModel.WeaponRowUiModel(
    name = name,
    ammoLabel = ammunition.displayLabel,
    type = type,
    icon = type.toUiModel(),
)