package pl.kacper.misterski.rangestats.feature.onboarding.domain.usecase

import pl.kacper.misterski.rangestats.feature.onboarding.domain.repository.OnboardingRepository

class IsOnboardingCompletedUseCase(private val onboardingRepository: OnboardingRepository) {
    suspend operator fun invoke(): Result<Boolean> = runCatching { onboardingRepository.isOnboardingCompleted() }
}
