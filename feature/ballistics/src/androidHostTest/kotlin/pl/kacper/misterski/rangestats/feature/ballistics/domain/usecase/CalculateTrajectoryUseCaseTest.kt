package pl.kacper.misterski.rangestats.feature.ballistics.domain.usecase

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import pl.kacper.misterski.rangestats.feature.ballistics.domain.exceptions.InvalidBallisticsInputException
import pl.kacper.misterski.rangestats.feature.ballistics.domain.model.BallisticsInput
import pl.kacper.misterski.rangestats.feature.ballistics.domain.model.BcModel
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith
import kotlin.test.assertTrue

class CalculateTrajectoryUseCaseTest {

    private val useCase = CalculateTrajectoryUseCase(Dispatchers.Unconfined)

    private fun rifleInput(
        targetDistanceMeters: Int = 200,
        zeroRangeMeters: Int = 100,
    ) = BallisticsInput(
        muzzleVelocityMs = 800.0,
        bulletMassGrains = 175.0,
        ballisticCoefficient = 0.475,
        zeroRangeMeters = zeroRangeMeters,
        targetDistanceMeters = targetDistanceMeters,
        scopeHeightMm = 38.0,
    )

    @Test
    fun `at zero range drop is approximately zero`() = runBlocking {
        // Given
        val input = rifleInput(targetDistanceMeters = 100, zeroRangeMeters = 100)

        // When
        val result = useCase(input).first()

        // Then
        assertTrue(
            kotlin.math.abs(result.dropMm) < 15.0,
            "Drop at zero range should be < 15mm, was ${result.dropMm} mm",
        )
    }

    @Test
    fun `beyond zero bullet drops below line of sight`() = runBlocking {
        // Given
        val input = rifleInput(targetDistanceMeters = 300, zeroRangeMeters = 100)

        // When
        val result = useCase(input).first()

        // Then
        assertTrue(
            result.dropMm < 0.0,
            "Drop beyond zero should be negative (below LoS)",
        )
    }

    @Test
    fun `velocity decreases with distance due to drag`() = runBlocking {
        // Given / When
        val close = useCase(rifleInput(targetDistanceMeters = 100)).first()
        val far = useCase(rifleInput(targetDistanceMeters = 500)).first()

        // Then
        assertTrue(
            far.remainingVelocityMs < close.remainingVelocityMs,
            "Velocity should decrease with distance",
        )
    }

    @Test
    fun `energy decreases with distance`() = runBlocking {
        // Given / When
        val close = useCase(rifleInput(targetDistanceMeters = 100)).first()
        val far = useCase(rifleInput(targetDistanceMeters = 500)).first()

        // Then
        assertTrue(
            far.energyJoules < close.energyJoules,
            "Energy should decrease with distance",
        )
    }

    @Test
    fun `time of flight increases with distance`() = runBlocking {
        // Given / When
        val close = useCase(rifleInput(targetDistanceMeters = 100)).first()
        val far = useCase(rifleInput(targetDistanceMeters = 300)).first()

        // Then
        assertTrue(
            far.timeOfFlightSec > close.timeOfFlightSec,
            "Time of flight should increase with distance",
        )
    }

    @Test
    fun `remaining velocity is less than muzzle velocity`() = runBlocking {
        // Given
        val input = rifleInput(targetDistanceMeters = 200)

        // When
        val result = useCase(input).first()

        // Then
        assertTrue(result.remainingVelocityMs < 800.0, "Remaining velocity should be less than muzzle velocity")
        assertTrue(result.remainingVelocityMs > 0.0, "Remaining velocity must be positive")
    }

    @Test
    fun `higher BC bullet retains more velocity at distance`() = runBlocking {
        // Given
        val lowBcInput = BallisticsInput(800.0, 175.0, 0.200, 100, 500)
        val highBcInput = BallisticsInput(800.0, 175.0, 0.500, 100, 500)

        // When
        val lowBc = useCase(lowBcInput).first()
        val highBc = useCase(highBcInput).first()

        // Then
        assertTrue(highBc.remainingVelocityMs > lowBc.remainingVelocityMs, "Higher BC should retain more velocity")
    }

    @Test
    fun `negative muzzle velocity fails with InvalidMuzzleVelocity`() {
        // Given
        val input = BallisticsInput(-100.0, 175.0, 0.475, 100, 200)

        // When / Then
        runBlocking {
            assertFailsWith<InvalidBallisticsInputException.InvalidMuzzleVelocity> {
                useCase(input).first()
            }
        }
    }

    @Test
    fun `zero BC fails with InvalidBallisticCoefficient`() {
        // Given
        val input = BallisticsInput(800.0, 175.0, 0.0, 100, 200)

        // When / Then
        runBlocking {
            assertFailsWith<InvalidBallisticsInputException.InvalidBallisticCoefficient> {
                useCase(input).first()
            }
        }
    }

    @Test
    fun `negative BC fails with InvalidBallisticCoefficient`() {
        // Given
        val input = BallisticsInput(800.0, 175.0, -0.3, 100, 200)

        // When / Then
        runBlocking {
            assertFailsWith<InvalidBallisticsInputException.InvalidBallisticCoefficient> {
                useCase(input).first()
            }
        }
    }

    @Test
    fun `zero target distance fails with InvalidTargetDistance`() {
        // Given
        val input = BallisticsInput(800.0, 175.0, 0.475, 100, 0)

        // When / Then
        runBlocking {
            assertFailsWith<InvalidBallisticsInputException.InvalidTargetDistance> {
                useCase(input).first()
            }
        }
    }

    @Test
    fun `zero zero-range fails with InvalidZeroRange`() {
        // Given
        val input = BallisticsInput(800.0, 175.0, 0.475, 0, 200)

        // When / Then
        runBlocking {
            assertFailsWith<InvalidBallisticsInputException.InvalidZeroRange> {
                useCase(input).first()
            }
        }
    }

    @Test
    fun `pistol caliber has large drop at 100m`() = runBlocking {
        // Given
        val input = BallisticsInput(
            muzzleVelocityMs = 370.0,
            bulletMassGrains = 115.0,
            ballisticCoefficient = 0.131,
            zeroRangeMeters = 25,
            targetDistanceMeters = 100,
            scopeHeightMm = 38.0,
        )

        // When
        val result = useCase(input).first()

        // Then
        assertTrue(
            result.dropMm < -200.0,
            "9mm at 100m (25m zero) should drop more than 200mm, was ${result.dropMm} mm",
        )
    }

    @Test
    fun `energy is positive at any valid distance`() = runBlocking {
        // Given
        val input = rifleInput(targetDistanceMeters = 1000)

        // When
        val result = useCase(input).first()

        // Then
        assertTrue(result.energyJoules > 0.0)
    }

    @Test
    fun `energy formula is consistent with mass and velocity`() = runBlocking {
        // Given
        val input = rifleInput(targetDistanceMeters = 200)

        // When
        val result = useCase(input).first()

        // Then
        val massKg = input.bulletMassGrains * 6.479891e-5
        val expectedEnergy = 0.5 * massKg * result.remainingVelocityMs * result.remainingVelocityMs
        assertTrue(
            kotlin.math.abs(expectedEnergy - result.energyJoules) < 0.01,
            "Energy should match E=0.5*m*v², expected $expectedEnergy, got ${result.energyJoules}",
        )
    }

    @Test
    fun `default bc model is G1`() {
        // Given / When
        val input = BallisticsInput(800.0, 175.0, 0.475, 100, 300)

        // Then
        assertEquals(BcModel.G1, input.bcModel)
    }

    @Test
    fun `G1 and G7 model produce different drop for the same BC value`() = runBlocking {
        // Given
        val g1Input = rifleInput(targetDistanceMeters = 500).copy(bcModel = BcModel.G1)
        val g7Input = rifleInput(targetDistanceMeters = 500).copy(bcModel = BcModel.G7)

        // When
        val g1Result = useCase(g1Input).first()
        val g7Result = useCase(g7Input).first()

        // Then
        assertTrue(
            kotlin.math.abs(g1Result.dropMm - g7Result.dropMm) > 1.0,
            "G1 (drop=${g1Result.dropMm}mm) and G7 (drop=${g7Result.dropMm}mm) should diverge at long range",
        )
    }

    @Test
    fun `G7 model retains more velocity than G1 for the same BC value at long range`() = runBlocking {
        // Given: G7 Cd is lower than G1 Cd across the whole velocity range, so for an
        // identical BC number a G7-modeled bullet decelerates less.
        val g1Input = rifleInput(targetDistanceMeters = 500).copy(bcModel = BcModel.G1)
        val g7Input = rifleInput(targetDistanceMeters = 500).copy(bcModel = BcModel.G7)

        // When
        val g1Result = useCase(g1Input).first()
        val g7Result = useCase(g7Input).first()

        // Then
        assertTrue(
            g7Result.remainingVelocityMs > g1Result.remainingVelocityMs,
            "G7 should retain more velocity than G1 for equal BC (G7 Cd < G1 Cd)",
        )
    }

    @Test
    fun `G7 model beyond zero also drops below line of sight`() = runBlocking {
        // Given
        val input = rifleInput(targetDistanceMeters = 300, zeroRangeMeters = 100).copy(bcModel = BcModel.G7)

        // When
        val result = useCase(input).first()

        // Then
        assertTrue(
            result.dropMm < 0.0,
            "Drop beyond zero should be negative (below LoS) for G7 too",
        )
    }
}