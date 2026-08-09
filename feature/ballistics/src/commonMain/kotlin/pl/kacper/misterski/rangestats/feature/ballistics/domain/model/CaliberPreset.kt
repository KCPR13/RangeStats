package pl.kacper.misterski.rangestats.feature.ballistics.domain.model

import pl.kacper.misterski.rangestats.core.domain.enums.Caliber

data class CaliberPreset(
    val caliber: Caliber,
    val displayLabel: String,
    val muzzleVelocityMs: Double,
    val bulletMassGrains: Double,
    val ballisticCoefficient: Double,
)
