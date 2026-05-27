package pl.kacper.misterski.rangestats.feature.ballistics.domain.usecase

import pl.kacper.misterski.rangestats.feature.ballistics.domain.model.BallisticsInput
import kotlin.test.Test
import kotlin.test.assertTrue

class CalculateTrajectoryUseCaseTest {

    private val useCase = CalculateTrajectoryUseCase()

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
    fun `at zero range drop is approximately zero`() {
        // Given
        val input = rifleInput(targetDistanceMeters = 100, zeroRangeMeters = 100)

        // When
        val result = useCase(input)

        // Then
        assertTrue(result.isSuccess)
        assertTrue(
            kotlin.math.abs(result.getOrThrow().dropMm) < 15.0,
            "Drop at zero range should be < 15mm, was ${result.getOrThrow().dropMm} mm",
        )
    }

    @Test
    fun `beyond zero bullet drops below line of sight`() {
        // Given
        val input = rifleInput(targetDistanceMeters = 300, zeroRangeMeters = 100)

        // When
        val result = useCase(input)

        // Then
        assertTrue(result.isSuccess)
        assertTrue(
            result.getOrThrow().dropMm < 0.0,
            "Drop beyond zero should be negative (below LoS)",
        )
    }

    @Test
    fun `velocity decreases with distance due to drag`() {
        // Given / When
        val close = useCase(rifleInput(targetDistanceMeters = 100)).getOrThrow()
        val far = useCase(rifleInput(targetDistanceMeters = 500)).getOrThrow()

        // Then
        assertTrue(
            far.remainingVelocityMs < close.remainingVelocityMs,
            "Velocity should decrease with distance",
        )
    }

    @Test
    fun `energy decreases with distance`() {
        // Given / When
        val close = useCase(rifleInput(targetDistanceMeters = 100)).getOrThrow()
        val far = useCase(rifleInput(targetDistanceMeters = 500)).getOrThrow()

        // Then
        assertTrue(
            far.energyJoules < close.energyJoules,
            "Energy should decrease with distance",
        )
    }

    @Test
    fun `time of flight increases with distance`() {
        // Given / When
        val close = useCase(rifleInput(targetDistanceMeters = 100)).getOrThrow()
        val far = useCase(rifleInput(targetDistanceMeters = 300)).getOrThrow()

        // Then
        assertTrue(
            far.timeOfFlightSec > close.timeOfFlightSec,
            "Time of flight should increase with distance",
        )
    }

    @Test
    fun `remaining velocity is less than muzzle velocity`() {
        // Given
        val input = rifleInput(targetDistanceMeters = 200)

        // When
        val result = useCase(input).getOrThrow()

        // Then
        assertTrue(result.remainingVelocityMs < 800.0, "Remaining velocity should be less than muzzle velocity")
        assertTrue(result.remainingVelocityMs > 0.0, "Remaining velocity must be positive")
    }

    @Test
    fun `higher BC bullet retains more velocity at distance`() {
        // Given
        val lowBcInput = BallisticsInput(800.0, 175.0, 0.200, 100, 500)
        val highBcInput = BallisticsInput(800.0, 175.0, 0.500, 100, 500)

        // When
        val lowBc = useCase(lowBcInput).getOrThrow()
        val highBc = useCase(highBcInput).getOrThrow()

        // Then
        assertTrue(highBc.remainingVelocityMs > lowBc.remainingVelocityMs, "Higher BC should retain more velocity")
    }

    @Test
    fun `negative muzzle velocity returns failure`() {
        // Given
        val input = BallisticsInput(-100.0, 175.0, 0.475, 100, 200)

        // When
        val result = useCase(input)

        // Then
        assertTrue(result.isFailure)
    }

    @Test
    fun `zero BC returns failure`() {
        // Given
        val input = BallisticsInput(800.0, 175.0, 0.0, 100, 200)

        // When
        val result = useCase(input)

        // Then
        assertTrue(result.isFailure)
    }

    @Test
    fun `negative BC returns failure`() {
        // Given
        val input = BallisticsInput(800.0, 175.0, -0.3, 100, 200)

        // When
        val result = useCase(input)

        // Then
        assertTrue(result.isFailure)
    }

    @Test
    fun `zero target distance returns failure`() {
        // Given
        val input = BallisticsInput(800.0, 175.0, 0.475, 100, 0)

        // When
        val result = useCase(input)

        // Then
        assertTrue(result.isFailure)
    }

    @Test
    fun `zero zero range returns failure`() {
        // Given
        val input = BallisticsInput(800.0, 175.0, 0.475, 0, 200)

        // When
        val result = useCase(input)

        // Then
        assertTrue(result.isFailure)
    }

    @Test
    fun `pistol caliber has large drop at 100m`() {
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
        val result = useCase(input).getOrThrow()

        // Then
        assertTrue(
            result.dropMm < -200.0,
            "9mm at 100m (25m zero) should drop more than 200mm, was ${result.dropMm} mm",
        )
    }

    @Test
    fun `energy is positive at any valid distance`() {
        // Given
        val input = rifleInput(targetDistanceMeters = 1000)

        // When
        val result = useCase(input).getOrThrow()

        // Then
        assertTrue(result.energyJoules > 0.0)
    }

    @Test
    fun `energy formula is consistent with mass and velocity`() {
        // Given
        val input = rifleInput(targetDistanceMeters = 200)

        // When
        val result = useCase(input).getOrThrow()

        // Then
        val massKg = input.bulletMassGrains * 6.479891e-5
        val expectedEnergy = 0.5 * massKg * result.remainingVelocityMs * result.remainingVelocityMs
        assertTrue(
            kotlin.math.abs(expectedEnergy - result.energyJoules) < 0.01,
            "Energy should match E=0.5*m*v², expected $expectedEnergy, got ${result.energyJoules}",
        )
    }
}
