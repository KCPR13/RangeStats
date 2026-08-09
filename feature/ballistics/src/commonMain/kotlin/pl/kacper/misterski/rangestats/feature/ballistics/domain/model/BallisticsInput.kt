package pl.kacper.misterski.rangestats.feature.ballistics.domain.model

import pl.kacper.misterski.rangestats.core.domain.Constants

data class BallisticsInput(
    val muzzleVelocityMs: Double,
    val bulletMassGrains: Double,
    val ballisticCoefficient: Double,
    val zeroRangeMeters: Int,
    val targetDistanceMeters: Int,
    val scopeHeightMm: Double = Constants.DEFAULT_SCOPE_HEIGHT_MM,
    val bcModel: BcModel = BcModel.G1,
)