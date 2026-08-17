package pl.kacper.misterski.rangestats.core.domain.usecase

import de.infix.testBalloon.framework.core.testSuite
import kotlinx.coroutines.flow.first
import pl.kacper.misterski.rangestats.core.domain.enums.Caliber
import pl.kacper.misterski.rangestats.core.domain.enums.ShotgunGauge
import pl.kacper.misterski.rangestats.core.domain.enums.WeaponType
import pl.kacper.misterski.rangestats.core.domain.exceptions.InvalidAmmunitionException
import pl.kacper.misterski.rangestats.core.domain.models.Ammunition
import pl.kacper.misterski.rangestats.core.testing.fake.FakeWeaponRepository
import pl.kacper.misterski.rangestats.core.testing.testDispatcher
import kotlin.test.assertFailsWith
import kotlin.test.assertTrue


val AddWeaponUseCaseTest by testSuite {

    test("caliber applicable to weapon type is saved successfully") {
        // Given
        val repository = FakeWeaponRepository()
        val useCase = AddWeaponUseCase(repository, testDispatcher)
        val ammunition = Ammunition.CaliberAmmo(Caliber.LUGER_9MM)

        // When
        useCase(name = "Glock 19", weaponType = WeaponType.PISTOL, ammunition = ammunition).first()

        // Then
        assertTrue(repository.weapons.any { it.name == "Glock 19" })
    }

    test("caliber not applicable to weapon type fails with InvalidAmmunitionException") {
        // Given: .357 Magnum is revolver-only
        val repository = FakeWeaponRepository()
        val useCase = AddWeaponUseCase(repository, testDispatcher)
        val ammunition = Ammunition.CaliberAmmo(Caliber.MAGNUM_357)

        // When
        assertFailsWith<InvalidAmmunitionException> {
            useCase(name = "AR-15", weaponType = WeaponType.RIFLE, ammunition = ammunition).first()
        }

        // Then
        assertTrue(repository.weapons.isEmpty())
    }

    test("gauge on a non-shotgun weapon fails with InvalidAmmunitionException") {
        // Given
        val repository = FakeWeaponRepository()
        val useCase = AddWeaponUseCase(repository, testDispatcher)
        val ammunition = Ammunition.GaugeAmmo(ShotgunGauge.GAUGE_12)

        // When / Then
        assertFailsWith<InvalidAmmunitionException> {
            useCase(name = "Glock 19", weaponType = WeaponType.PISTOL, ammunition = ammunition).first()
        }
    }

    test("gauge on a shotgun is saved successfully") {
        // Given
        val repository = FakeWeaponRepository()
        val useCase = AddWeaponUseCase(repository, testDispatcher)
        val ammunition = Ammunition.GaugeAmmo(ShotgunGauge.GAUGE_12)

        // When
        useCase(
            name = "Remington 870",
            weaponType = WeaponType.SHOTGUN,
            ammunition = ammunition
        ).first()

        // Then
        assertTrue(repository.weapons.any { it.name == "Remington 870" })
    }
}
