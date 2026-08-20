package pl.kacper.misterski.rangestats.feature.ballistics.domain.validator

import pl.kacper.misterski.rangestats.feature.ballistics.domain.model.BallisticsInput
import pl.kacper.misterski.rangestats.feature.ballistics.domain.model.BcModel

class BallisticsInputValidator {

    @Suppress("LongParameterList")
    fun validate(
        muzzleVelocity: String,
        bulletMass: String,
        ballisticCoefficient: String,
        zeroRange: String,
        targetDistance: String,
        scopeHeight: String,
        bcModel: BcModel,
    ): BallisticsInput? {
        val v0 = muzzleVelocity.toDoubleOrNull()?.takeIf { it > 0.0 } ?: return null
        val mass = bulletMass.toDoubleOrNull()?.takeIf { it > 0.0 } ?: return null
        val bc = ballisticCoefficient.toDoubleOrNull()?.takeIf { it > 0.0 } ?: return null
        val zero = zeroRange.toIntOrNull()?.takeIf { it > 0 } ?: return null
        val distance = targetDistance.toIntOrNull()?.takeIf { it > 0 } ?: return null
        val scope = scopeHeight.toDoubleOrNull()?.takeIf { it > 0.0 } ?: return null

        return BallisticsInput(
            muzzleVelocityMs = v0,
            bulletMassGrains = mass,
            ballisticCoefficient = bc,
            zeroRangeMeters = zero,
            targetDistanceMeters = distance,
            scopeHeightMm = scope,
            bcModel = bcModel,
        )
    }
}
