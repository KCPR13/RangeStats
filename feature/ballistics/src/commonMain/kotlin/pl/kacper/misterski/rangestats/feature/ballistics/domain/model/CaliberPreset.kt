package pl.kacper.misterski.rangestats.feature.ballistics.domain.model

data class CaliberPreset(
    val name: String,
    val muzzleVelocityMs: Double,
    val bulletMassGrains: Double,
    val ballisticCoefficient: Double,
)
