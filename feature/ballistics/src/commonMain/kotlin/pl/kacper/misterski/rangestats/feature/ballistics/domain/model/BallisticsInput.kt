package pl.kacper.misterski.rangestats.feature.ballistics.domain.model

data class BallisticsInput(
    val muzzleVelocityMs: Double,
    val bulletMassGrains: Double,
    val ballisticCoefficient: Double,
    val zeroRangeMeters: Int,
    val targetDistanceMeters: Int,
    val scopeHeightMm: Double = 38.0, // TODO
)