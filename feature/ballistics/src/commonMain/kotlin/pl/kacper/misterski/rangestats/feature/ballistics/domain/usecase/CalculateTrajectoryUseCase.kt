package pl.kacper.misterski.rangestats.feature.ballistics.domain.usecase

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import pl.kacper.misterski.rangestats.feature.ballistics.domain.converter.dragCoefficient
import pl.kacper.misterski.rangestats.feature.ballistics.domain.exceptions.InvalidBallisticsInputException
import pl.kacper.misterski.rangestats.feature.ballistics.domain.model.BallisticsInput
import pl.kacper.misterski.rangestats.feature.ballistics.domain.model.BallisticsResult
import pl.kacper.misterski.rangestats.feature.ballistics.domain.model.BcModel
import kotlin.math.PI
import kotlin.math.cos
import kotlin.math.sin
import kotlin.math.sqrt

class CalculateTrajectoryUseCase(
    private val ioDispatcher: CoroutineDispatcher,
) {

    operator fun invoke(input: BallisticsInput): Flow<BallisticsResult> = flow {
        validate(input)

        val bcMetric = input.ballisticCoefficient * BC_CONVERSION
        val massKg = input.bulletMassGrains * GRAIN_TO_KG
        val scopeHeightM = input.scopeHeightMm / 1000.0

        val boreAngle = findZeroAngle(
            v0 = input.muzzleVelocityMs,
            bcMetric = bcMetric,
            bcModel = input.bcModel,
            zeroRangeM = input.zeroRangeMeters.toDouble(),
            scopeHeightM = scopeHeightM,
        )

        emit(
            simulate(
                v0 = input.muzzleVelocityMs,
                bcMetric = bcMetric,
                bcModel = input.bcModel,
                massKg = massKg,
                boreAngleRad = boreAngle,
                scopeHeightM = scopeHeightM,
                targetDistanceM = input.targetDistanceMeters.toDouble(),
            ),
        )
    }.flowOn(ioDispatcher)

    private fun validate(input: BallisticsInput) {
        if (input.muzzleVelocityMs <= 0.0) throw InvalidBallisticsInputException.InvalidMuzzleVelocity
        if (input.ballisticCoefficient <= 0.0) throw InvalidBallisticsInputException.InvalidBallisticCoefficient
        if (input.bulletMassGrains <= 0.0) throw InvalidBallisticsInputException.InvalidBulletMass
        if (input.zeroRangeMeters <= 0) throw InvalidBallisticsInputException.InvalidZeroRange
        if (input.targetDistanceMeters <= 0) throw InvalidBallisticsInputException.InvalidTargetDistance
    }

    private fun findZeroAngle(
        v0: Double,
        bcMetric: Double,
        bcModel: BcModel,
        zeroRangeM: Double,
        scopeHeightM: Double,
    ): Double {
        var lo = -0.1
        var hi = 0.1
        repeat(60) {
            val mid = (lo + hi) / 2.0
            val y = integrateY(v0, bcMetric, bcModel, mid, zeroRangeM)
            if (y < scopeHeightM) lo = mid else hi = mid
        }
        return (lo + hi) / 2.0
    }

    private fun integrateY(v0: Double, bcMetric: Double, bcModel: BcModel, angleRad: Double, targetX: Double): Double {
        var vx = v0 * cos(angleRad)
        var vy = v0 * sin(angleRad)
        var x = 0.0
        var y = 0.0
        var t = 0.0

        while (x < targetX && t < MAX_FLIGHT_SEC) {
            val v = sqrt(vx * vx + vy * vy)
            val drag = dragRetardation(v, bcMetric, bcModel)
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
        bcModel: BcModel,
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
            val drag = dragRetardation(v, bcMetric, bcModel)
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

    private fun dragRetardation(v: Double, bcMetric: Double, bcModel: BcModel): Double {
        val mach = v / SPEED_OF_SOUND
        val cd = dragCoefficient(bcModel, mach)
        return DRAG_FORM_FACTOR * AIR_DENSITY * cd * v / bcMetric
    }

    companion object {
        private const val GRAVITY = 9.81
        private const val AIR_DENSITY = 1.225
        private const val BC_CONVERSION = 703.07
        private const val GRAIN_TO_KG = 6.479891e-5
        private const val DT = 0.001
        private const val MAX_FLIGHT_SEC = 10.0
        private const val SPEED_OF_SOUND = 340.3
        private val DRAG_FORM_FACTOR = PI / 8.0
    }
}
