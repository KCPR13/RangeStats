package pl.kacper.misterski.rangestats.feature.onboarding.domain.usecase

import kotlinx.coroutines.test.runTest
import pl.kacper.misterski.rangestats.core.domain.enums.UnitSystem
import pl.kacper.misterski.rangestats.core.domain.models.UserProfile
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class CompleteOnboardingUseCaseTest {

    private val repo = FakeOnboardingRepository()
    private val useCase = CompleteOnboardingUseCase(repo)

    private fun profile(
        name: String = "Test User",
        units: UnitSystem = UnitSystem.METRIC,
        distance: Int = 100,
    ) = UserProfile(displayName = name, units = units, defaultDistanceMeters = distance)

    @Test
    fun `marks onboarding as completed in repository`() = runTest {
        // Given
        val profile = profile()

        // When
        useCase(profile)

        // Then
        assertTrue(repo.onboardingCompleted)
    }

    @Test
    fun `saves profile to repository`() = runTest {
        // Given
        val profile = profile(name = "Sniper One", units = UnitSystem.IMPERIAL, distance = 300)

        // When
        useCase(profile)

        // Then
        assertEquals(profile, repo.lastSavedProfile)
    }

    @Test
    fun `invokes completeOnboarding on repository exactly once per call`() = runTest {
        // Given
        val profile = profile()

        // When
        useCase(profile)

        // Then
        assertEquals(1, repo.completeCallCount)
    }
}
