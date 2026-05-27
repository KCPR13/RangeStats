package pl.kacper.misterski.rangestats.feature.ballistics.domain.model

data class BallisticsResult(
    val dropMm: Double,
    val remainingVelocityMs: Double,
    val energyJoules: Double,
    val timeOfFlightSec: Double,
)