package pl.kacper.misterski.rangestats.feature.settings.domain.usecase

import kotlinx.coroutines.runBlocking
import pl.kacper.misterski.rangestats.core.domain.enums.Caliber
import pl.kacper.misterski.rangestats.core.domain.enums.ShotgunGauge
import pl.kacper.misterski.rangestats.core.domain.enums.WeaponType
import pl.kacper.misterski.rangestats.core.domain.exceptions.InvalidAmmunitionException
import pl.kacper.misterski.rangestats.core.domain.models.Ammunition
import pl.kacper.misterski.rangestats.core.domain.models.Weapon
import pl.kacper.misterski.rangestats.feature.settings.domain.repository.WeaponRepository
import kotlin.test.Test
import kotlin.test.assertIs
import kotlin.test.assertTrue

private class FakeWeaponRepository : WeaponRepository {
    val weapons = mutableListOf<Weapon>()
    override suspend fun getAllWeapons(): List<Weapon> = weapons
    override suspend fun addWeapon(weapon: Weapon) {
        weapons += weapon
    }
    override suspend fun deleteWeapon(name: String) {
        weapons.removeAll { it.name == name }
    }
}

class AddWeaponUseCaseTest {

    private val repository = FakeWeaponRepository()
    private val useCase = AddWeaponUseCase(repository)

    @Test
    fun `caliber applicable to weapon type is saved successfully`(): Unit = runBlocking {
        // Given
        val ammunition = Ammunition.CaliberAmmo(Caliber.LUGER_9MM)

        // When
        val result = useCase(name = "Glock 19", type = WeaponType.PISTOL, ammunition = ammunition)

        // Then
        assertTrue(result.isSuccess)
        assertTrue(repository.weapons.any { it.name == "Glock 19" })
    }

    @Test
    fun `caliber not applicable to weapon type fails with InvalidAmmunitionException`(): Unit = runBlocking {
        // Given: .357 Magnum is revolver-only
        val ammunition = Ammunition.CaliberAmmo(Caliber.MAGNUM_357)

        // When
        val result = useCase(name = "AR-15", type = WeaponType.RIFLE, ammunition = ammunition)

        // Then
        assertTrue(result.isFailure)
        assertIs<InvalidAmmunitionException>(result.exceptionOrNull())
        assertTrue(repository.weapons.isEmpty())
    }

    @Test
    fun `gauge on a non-shotgun weapon fails with InvalidAmmunitionException`(): Unit = runBlocking {
        // Given
        val ammunition = Ammunition.GaugeAmmo(ShotgunGauge.GAUGE_12)

        // When
        val result = useCase(name = "Glock 19", type = WeaponType.PISTOL, ammunition = ammunition)

        // Then
        assertTrue(result.isFailure)
        assertIs<InvalidAmmunitionException>(result.exceptionOrNull())
    }

    @Test
    fun `gauge on a shotgun is saved successfully`(): Unit = runBlocking {
        // Given
        val ammunition = Ammunition.GaugeAmmo(ShotgunGauge.GAUGE_12)

        // When
        val result = useCase(name = "Remington 870", type = WeaponType.SHOTGUN, ammunition = ammunition)

        // Then
        assertTrue(result.isSuccess)
        assertTrue(repository.weapons.any { it.name == "Remington 870" })
    }
}