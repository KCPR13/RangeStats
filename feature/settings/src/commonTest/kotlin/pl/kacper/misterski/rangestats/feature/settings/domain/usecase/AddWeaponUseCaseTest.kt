package pl.kacper.misterski.rangestats.feature.settings.domain.usecase

import de.infix.testBalloon.framework.core.testSuite
import pl.kacper.misterski.rangestats.core.domain.enums.Caliber
import pl.kacper.misterski.rangestats.core.domain.enums.ShotgunGauge
import pl.kacper.misterski.rangestats.core.domain.enums.WeaponType
import pl.kacper.misterski.rangestats.core.domain.exceptions.InvalidAmmunitionException
import pl.kacper.misterski.rangestats.core.domain.models.Ammunition
import kotlin.test.assertIs
import kotlin.test.assertTrue


val AddWeaponUseCaseTest by testSuite {

    test("caliber applicable to weapon type is saved successfully") {
        // Given
        val repository = FakeWeaponRepository()
        val useCase = AddWeaponUseCase(repository)
        val ammunition = Ammunition.CaliberAmmo(Caliber.LUGER_9MM)

        // When
        val result =
            useCase(name = "Glock 19", weaponType = WeaponType.PISTOL, ammunition = ammunition)

        // Then
        assertTrue(result.isSuccess)
        assertTrue(repository.weapons.any { it.name == "Glock 19" })
    }

    test("caliber not applicable to weapon type fails with InvalidAmmunitionException") {
        // Given: .357 Magnum is revolver-only
        val repository = FakeWeaponRepository()
        val useCase = AddWeaponUseCase(repository)
        val ammunition = Ammunition.CaliberAmmo(Caliber.MAGNUM_357)

        // When
        val result = useCase(name = "AR-15", weaponType = WeaponType.RIFLE, ammunition = ammunition)

        // Then
        assertTrue(result.isFailure)
        assertIs<InvalidAmmunitionException>(result.exceptionOrNull())
        assertTrue(repository.weapons.isEmpty())
    }

    test("gauge on a non-shotgun weapon fails with InvalidAmmunitionException") {
        // Given
        val repository = FakeWeaponRepository()
        val useCase = AddWeaponUseCase(repository)
        val ammunition = Ammunition.GaugeAmmo(ShotgunGauge.GAUGE_12)

        // When
        val result =
            useCase(name = "Glock 19", weaponType = WeaponType.PISTOL, ammunition = ammunition)

        // Then
        assertTrue(result.isFailure)
        assertIs<InvalidAmmunitionException>(result.exceptionOrNull())
    }

    test("gauge on a shotgun is saved successfully") {
        // Given
        val repository = FakeWeaponRepository()
        val useCase = AddWeaponUseCase(repository)
        val ammunition = Ammunition.GaugeAmmo(ShotgunGauge.GAUGE_12)

        // When
        val result = useCase(
            name = "Remington 870",
            weaponType = WeaponType.SHOTGUN,
            ammunition = ammunition
        )

        // Then
        assertTrue(result.isSuccess)
        assertTrue(repository.weapons.any { it.name == "Remington 870" })
    }
}