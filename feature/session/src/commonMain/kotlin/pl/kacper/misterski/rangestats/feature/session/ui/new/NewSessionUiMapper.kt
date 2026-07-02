package pl.kacper.misterski.rangestats.feature.session.ui.new

import pl.kacper.misterski.rangestats.core.domain.models.Weapon
import pl.kacper.misterski.rangestats.core.ui.component.toUiModel

fun Weapon.toUiModel(): NewSessionUiModel.WeaponRowUiModel = NewSessionUiModel.WeaponRowUiModel(
    name = name,
    caliber = caliber,
    type = type,
    icon = type.toUiModel(),
)
