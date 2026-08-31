package pl.kacper.misterski.rangestats.feature.settings.ui.settings

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import pl.kacper.misterski.rangestats.core.domain.converter.UnitConverter
import pl.kacper.misterski.rangestats.core.domain.enums.UnitSystem
import pl.kacper.misterski.rangestats.core.domain.models.UserProfile
import pl.kacper.misterski.rangestats.core.testing.fake.FakeNavigator
import pl.kacper.misterski.rangestats.core.testing.fake.FakeWeaponRepository
import pl.kacper.misterski.rangestats.core.testing.testDispatcher
import pl.kacper.misterski.rangestats.core.domain.usecase.DeleteWeaponUseCase
import pl.kacper.misterski.rangestats.core.domain.usecase.GetWeaponsUseCase
import pl.kacper.misterski.rangestats.feature.settings.domain.usecase.FakeProfileRepository
import pl.kacper.misterski.rangestats.feature.settings.domain.usecase.GetUserProfileUseCase
import pl.kacper.misterski.rangestats.feature.settings.domain.usecase.UpdateUserProfileUseCase
import pl.kacper.misterski.rangestats.feature.settings.ui.WeaponAddedNotifier
import kotlin.test.AfterTest
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertTrue

@OptIn(ExperimentalCoroutinesApi::class)
@RunWith(RobolectricTestRunner::class)
class SettingsViewModelTest {

    @BeforeTest
    fun setUp() {
        Dispatchers.setMain(UnconfinedTestDispatcher())
    }

    @AfterTest
    fun tearDown() {
        Dispatchers.resetMain()
    }

    private fun viewModel(initial: UserProfile): SettingsViewModel {
        val repository = FakeProfileRepository().apply { profile = initial }
        return SettingsViewModel(
            getWeaponsUseCase = GetWeaponsUseCase(FakeWeaponRepository(), testDispatcher),
            getUserProfileUseCase = GetUserProfileUseCase(repository, testDispatcher),
            updateUserProfileUseCase = UpdateUserProfileUseCase(repository, testDispatcher),
            deleteWeaponUseCase = DeleteWeaponUseCase(FakeWeaponRepository(), testDispatcher),
            unitConverter = UnitConverter(),
            navigator = FakeNavigator(),
            weaponAddedNotifier = WeaponAddedNotifier(),
        )
    }

    @Test
    fun `increment and decrement move by 1 meter in metric`() = runTest {
        val vm = viewModel(UserProfile("U", UnitSystem.METRIC, 25))
        vm.onAction(SettingsAction.OnStart)

        vm.onAction(SettingsAction.IncrementDistance)
        assertEquals(26, vm.uiModel.value.profile.defaultDistanceMeters)

        vm.onAction(SettingsAction.DecrementDistance)
        assertEquals(25, vm.uiModel.value.profile.defaultDistanceMeters)
    }

    @Test
    fun `increment in imperial moves display by 1 yard and updates canonical meters`() = runTest {
        val vm = viewModel(UserProfile("U", UnitSystem.IMPERIAL, 25))
        vm.onAction(SettingsAction.OnStart)

        // 25 m -> 27 yd (displayed), +1 yd -> 28 yd -> round(28 * 0.9144) = 26 m
        vm.onAction(SettingsAction.IncrementDistance)

        assertEquals(26, vm.uiModel.value.profile.defaultDistanceMeters)
    }

    @Test
    fun `switching units and back preserves canonical meters exactly`() = runTest {
        val vm = viewModel(UserProfile("U", UnitSystem.METRIC, 137))
        vm.onAction(SettingsAction.OnStart)

        vm.onAction(SettingsAction.UnitSystemChanged(UnitSystem.IMPERIAL.ordinal))
        vm.onAction(SettingsAction.UnitSystemChanged(UnitSystem.METRIC.ordinal))

        assertEquals(137, vm.uiModel.value.profile.defaultDistanceMeters)
    }

    @Test
    fun `manual input in imperial converts to meters on confirm`() = runTest {
        val vm = viewModel(UserProfile("U", UnitSystem.IMPERIAL, 25))
        vm.onAction(SettingsAction.OnStart)
        vm.onAction(SettingsAction.ShowDistanceEditDialog)

        vm.onAction(SettingsAction.DistanceInputChanged("100"))
        assertTrue(vm.uiModel.value.distanceEdit.isInputValid)

        vm.onAction(SettingsAction.ConfirmDistanceInput)

        // 100 yd -> round(100 * 0.9144) = 91 m
        assertEquals(91, vm.uiModel.value.profile.defaultDistanceMeters)
    }

    @Test
    fun `manual input outside range is marked invalid`() = runTest {
        val vm = viewModel(UserProfile("U", UnitSystem.METRIC, 25))
        vm.onAction(SettingsAction.OnStart)
        vm.onAction(SettingsAction.ShowDistanceEditDialog)

        vm.onAction(SettingsAction.DistanceInputChanged("500"))

        assertFalse(vm.uiModel.value.distanceEdit.isInputValid)
    }

    @Test
    fun `decrement below minimum clamps at minimum in imperial`() = runTest {
        val vm = viewModel(UserProfile("U", UnitSystem.IMPERIAL, 5))
        vm.onAction(SettingsAction.OnStart)

        vm.onAction(SettingsAction.DecrementDistance)

        assertEquals(5, vm.uiModel.value.profile.defaultDistanceMeters)
    }

    @Test
    fun `increment above maximum clamps at maximum in metric`() = runTest {
        val vm = viewModel(UserProfile("U", UnitSystem.METRIC, 300))
        vm.onAction(SettingsAction.OnStart)

        vm.onAction(SettingsAction.IncrementDistance)

        assertEquals(300, vm.uiModel.value.profile.defaultDistanceMeters)
    }
}
