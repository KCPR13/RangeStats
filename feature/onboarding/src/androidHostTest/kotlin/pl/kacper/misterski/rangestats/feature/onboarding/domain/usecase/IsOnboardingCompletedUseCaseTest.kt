package pl.kacper.misterski.rangestats.feature.onboarding.domain.usecase

import kotlinx.coroutines.test.runTest
import kotlin.test.Test
import kotlin.test.assertFalse
import kotlin.test.assertTrue

class IsOnboardingCompletedUseCaseTest {

    private val repo = FakeOnboardingRepository()
    private val useCase = IsOnboardingCompletedUseCase(repo)

    @Test
    fun `returns false when onboarding not completed`() = runTest {
        // Given
        repo.onboardingCompleted = false

        // When
        val result = useCase()

        // Then
        assertFalse(result.getOrThrow())
    }

    @Test
    fun `returns true when onboarding is completed`() = runTest {
        // Given
        repo.onboardingCompleted = true

        // When
        val result = useCase()

        // Then
        assertTrue(result.getOrThrow())
    }

    @Test
    fun `reflects repository state change`() = runTest {
        // Given
        assertFalse(useCase().getOrThrow())

        // When
        repo.onboardingCompleted = true

        // Then
        assertTrue(useCase().getOrThrow())
    }
}
