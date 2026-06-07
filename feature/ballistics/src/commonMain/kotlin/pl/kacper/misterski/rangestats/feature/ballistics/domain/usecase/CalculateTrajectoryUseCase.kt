package pl.kacper.misterski.rangestats.feature.ballistics.domain.usecase

import pl.kacper.misterski.rangestats.feature.ballistics.domain.model.BallisticsInput
import pl.kacper.misterski.rangestats.feature.ballistics.domain.model.BallisticsResult
import kotlin.math.cos
import kotlin.math.sin
import kotlin.math.sqrt

class CalculateTrajectoryUseCase {

    // TODO
    operator fun invoke(input: BallisticsInput): Result<BallisticsResult> = runCatching {
        require(input.muzzleVelocityMs > 0.0) { "Muzzle velocity must be positive" }
        require(input.ballisticCoefficient > 0.0) { "BC must be positive" }
        require(input.bulletMassGrains > 0.0) { "Bullet mass must be positive" }
        require(input.zeroRangeMeters > 0) { "Zero range must be positive" }
        require(input.targetDistanceMeters > 0) { "Target distance must be positive" }

        val bcMetric = input.ballisticCoefficient * BC_CONVERSION
        val massKg = input.bulletMassGrains * GRAIN_TO_KG
        val scopeHeightM = input.scopeHeightMm / 1000.0

        val boreAngle = findZeroAngle(
            v0 = input.muzzleVelocityMs,
            bcMetric = bcMetric,
            zeroRangeM = input.zeroRangeMeters.toDouble(),
            scopeHeightM = scopeHeightM,
        )

        simulate(
            v0 = input.muzzleVelocityMs,
            bcMetric = bcMetric,
            massKg = massKg,
            boreAngleRad = boreAngle,
            scopeHeightM = scopeHeightM,
            targetDistanceM = input.targetDistanceMeters.toDouble(),
        )
    }

    private fun findZeroAngle(
        v0: Double,
        bcMetric: Double,
        zeroRangeM: Double,
        scopeHeightM: Double,
    ): Double {
        var lo = -0.1
        var hi = 0.1
        repeat(60) {
            val mid = (lo + hi) / 2.0
            val y = integrateY(v0, bcMetric, mid, zeroRangeM)
            if (y < scopeHeightM) lo = mid else hi = mid
        }
        return (lo + hi) / 2.0
    }

    private fun integrateY(v0: Double, bcMetric: Double, angleRad: Double, targetX: Double): Double {
        var vx = v0 * cos(angleRad)
        var vy = v0 * sin(angleRad)
        var x = 0.0
        var y = 0.0
        var t = 0.0

        while (x < targetX && t < MAX_FLIGHT_SEC) {
            val v = sqrt(vx * vx + vy * vy)
            val drag = AIR_DENSITY * v / (2.0 * bcMetric)
            vx -= drag * vx * DT
            vy -= (drag * vy + GRAVITY) * DT
            x += vx * DT
            y += vy * DT
            t += DT
        }
        return y
    }

    private fun simulate(
        v0: Double,
        bcMetric: Double,
        massKg: Double,
        boreAngleRad: Double,
        scopeHeightM: Double,
        targetDistanceM: Double,
    ): BallisticsResult {
        var vx = v0 * cos(boreAngleRad)
        var vy = v0 * sin(boreAngleRad)
        var x = 0.0
        var y = 0.0
        var t = 0.0

        while (x < targetDistanceM && t < MAX_FLIGHT_SEC) {
            val v = sqrt(vx * vx + vy * vy)
            val drag = AIR_DENSITY * v / (2.0 * bcMetric)
            vx -= drag * vx * DT
            vy -= (drag * vy + GRAVITY) * DT
            x += vx * DT
            y += vy * DT
            t += DT
        }

        val vFinal = sqrt(vx * vx + vy * vy)
        return BallisticsResult(
            dropMm = (y - scopeHeightM) * 1000.0,
            remainingVelocityMs = vFinal,
            energyJoules = 0.5 * massKg * vFinal * vFinal,
            timeOfFlightSec = t,
        )
    }

    companion object {
        private const val GRAVITY = 9.81
        private const val AIR_DENSITY = 1.225
        private const val BC_CONVERSION = 703.07
        private const val GRAIN_TO_KG = 6.479891e-5
        private const val DT = 0.001
        private const val MAX_FLIGHT_SEC = 10.0
    }
}
