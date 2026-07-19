package pl.kacper.misterski.rangestats.core.domain.enums

import kotlin.test.Test
import kotlin.test.assertFalse
import kotlin.test.assertTrue

class CaliberTest {

    private fun calibersFor(type: WeaponType): List<Caliber> =
        Caliber.entries.filter { type in it.applicableTo }

    @Test
    fun `LR_22 is applicable to pistol, revolver and rifle`() {
        // Given
        val caliber = Caliber.LR_22

        // When / Then
        assertTrue(WeaponType.PISTOL in caliber.applicableTo)
        assertTrue(WeaponType.REVOLVER in caliber.applicableTo)
        assertTrue(WeaponType.RIFLE in caliber.applicableTo)
    }

    @Test
    fun `PCC calibers are applicable to both pistol and rifle`() {
        // Given
        val pccCalibers = listOf(Caliber.LUGER_9MM, Caliber.SW_40, Caliber.AUTO_10MM, Caliber.ACP_45)

        // When / Then
        pccCalibers.forEach { caliber ->
            assertTrue(WeaponType.PISTOL in caliber.applicableTo, "${caliber.name} should apply to PISTOL")
            assertTrue(WeaponType.RIFLE in caliber.applicableTo, "${caliber.name} should apply to RIFLE")
        }
    }

    @Test
    fun `revolver-only caliber does not apply to pistol or rifle`() {
        // Given
        val caliber = Caliber.MAGNUM_357

        // When / Then
        assertFalse(WeaponType.PISTOL in caliber.applicableTo)
        assertFalse(WeaponType.RIFLE in caliber.applicableTo)
        assertTrue(WeaponType.REVOLVER in caliber.applicableTo)
    }

    @Test
    fun `filtering by PISTOL never returns a revolver-only or rifle-only caliber`() {
        // Given / When
        val result = calibersFor(WeaponType.PISTOL)

        // Then
        assertFalse(result.contains(Caliber.MAGNUM_357))
        assertFalse(result.contains(Caliber.REMINGTON_223))
        assertTrue(result.contains(Caliber.LR_22))
    }

    @Test
    fun `filtering by RIFLE includes standard rifle calibers, PCC calibers and 22 LR`() {
        // Given / When
        val result = calibersFor(WeaponType.RIFLE)

        // Then
        assertTrue(result.contains(Caliber.REMINGTON_223))
        assertTrue(result.contains(Caliber.LUGER_9MM))
        assertTrue(result.contains(Caliber.LR_22))
        assertFalse(result.contains(Caliber.MAGNUM_357))
    }

    @Test
    fun `no Caliber entry is applicable to SHOTGUN`() {
        // Given / When
        val result = calibersFor(WeaponType.SHOTGUN)

        // Then
        assertTrue(result.isEmpty(), "Shotgun must use ShotgunGauge, not Caliber")
    }
}