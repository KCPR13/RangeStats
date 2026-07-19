package pl.kacper.misterski.rangestats.feature.onboarding.ui

import pl.kacper.misterski.rangestats.core.domain.models.Weapon
import pl.kacper.misterski.rangestats.core.ui.component.toUiModel

fun Weapon.toUiModel(): OnboardingUiModel.WeaponRowUiModel = OnboardingUiModel.WeaponRowUiModel(
    name = name,
    ammoLabel = ammunition.displayLabel,
    icon = type.toUiModel(),
)