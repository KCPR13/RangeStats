package pl.kacper.misterski.rangestats.feature.ballistics.domain.validator

import pl.kacper.misterski.rangestats.feature.ballistics.domain.model.BcModel
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNull

class BallisticsInputValidatorTest {

    private val validator = BallisticsInputValidator()

    @Suppress("LongParameterList")
    private fun validate(
        muzzleVelocity: String = "800",
        bulletMass: String = "175",
        ballisticCoefficient: String = "0.475",
        zeroRange: String = "100",
        targetDistance: String = "300",
        scopeHeight: String = "38",
        bcModel: BcModel = BcModel.G1,
    ) = validator.validate(
        muzzleVelocity = muzzleVelocity,
        bulletMass = bulletMass,
        ballisticCoefficient = ballisticCoefficient,
        zeroRange = zeroRange,
        targetDistance = targetDistance,
        scopeHeight = scopeHeight,
        bcModel = bcModel,
    )

    @Test
    fun `all fields valid returns parsed input`() {
        // Given / When
        val result = validate()

        // Then
        assertEquals(800.0, result?.muzzleVelocityMs)
        assertEquals(175.0, result?.bulletMassGrains)
        assertEquals(0.475, result?.ballisticCoefficient)
        assertEquals(100, result?.zeroRangeMeters)
        assertEquals(300, result?.targetDistanceMeters)
        assertEquals(38.0, result?.scopeHeightMm)
        assertEquals(BcModel.G1, result?.bcModel)
    }

    @Test
    fun `empty field returns null`() {
        // Given / When / Then
        assertNull(validate(muzzleVelocity = ""))
    }

    @Test
    fun `non numeric field returns null`() {
        // Given / When / Then
        assertNull(validate(bulletMass = "abc"))
    }

    @Test
    fun `zero value returns null`() {
        // Given / When / Then
        assertNull(validate(ballisticCoefficient = "0"))
    }

    @Test
    fun `negative value returns null`() {
        // Given / When / Then
        assertNull(validate(zeroRange = "-100"))
    }

    @Test
    fun `zero target distance returns null`() {
        // Given / When / Then
        assertNull(validate(targetDistance = "0"))
    }

    @Test
    fun `negative scope height returns null`() {
        // Given / When / Then
        assertNull(validate(scopeHeight = "-38"))
    }
}
