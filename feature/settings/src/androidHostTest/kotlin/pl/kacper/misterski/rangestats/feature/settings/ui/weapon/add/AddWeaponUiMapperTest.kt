package pl.kacper.misterski.rangestats.feature.settings.ui.weapon.add

import pl.kacper.misterski.rangestats.core.domain.enums.Caliber
import pl.kacper.misterski.rangestats.core.domain.enums.ShotgunGauge
import pl.kacper.misterski.rangestats.core.domain.enums.WeaponType
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class AddWeaponUiMapperTest {

    @Test
    fun `toAmmoOptionUiModels SHOTGUN returns ShotgunGauge options, not Caliber`() {
        // Given / When
        val options = WeaponType.SHOTGUN.toAmmoOptionUiModels()

        // Then
        assertEquals(ShotgunGauge.entries.size, options.size)
        assertTrue(options.all { option -> ShotgunGauge.entries.any { it.name == option.key } })
    }

    @Test
    fun `toAmmoOptionUiModels PISTOL returns only calibers applicable to PISTOL`() {
        // Given / When
        val options = WeaponType.PISTOL.toAmmoOptionUiModels()

        // Then
        assertTrue(options.all { option -> WeaponType.PISTOL in Caliber.valueOf(option.key).applicableTo })
        assertTrue(options.none { it.key == Caliber.MAGNUM_357.name })
    }

    @Test
    fun `toAmmoOptionUiModels RIFLE includes PCC calibers and 22 LR`() {
        // Given / When
        val options = WeaponType.RIFLE.toAmmoOptionUiModels()

        // Then
        assertTrue(options.any { it.key == Caliber.LUGER_9MM.name })
        assertTrue(options.any { it.key == Caliber.LR_22.name })
    }

    @Test
    fun `first option is selected by default`() {
        // Given / When
        val options = WeaponType.PISTOL.toAmmoOptionUiModels()

        // Then
        assertTrue(options.first().selected)
        assertTrue(options.drop(1).none { it.selected })
    }
}