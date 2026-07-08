package pl.kacper.misterski.rangestats.feature.settings.ui.weapon.add

import pl.kacper.misterski.rangestats.feature.settings.domain.enums.Caliber

fun defaultCaliberUiModels(): List<AddWeaponUiModel.CaliberUiModel> =
    Caliber.entries.mapIndexed { index, caliber ->
        AddWeaponUiModel.CaliberUiModel(name = caliber.label, selected = index == 0)
    }
