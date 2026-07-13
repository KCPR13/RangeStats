package pl.kacper.misterski.rangestats.feature.onboarding.domain.usecase

import pl.kacper.misterski.rangestats.core.domain.models.UserProfile
import pl.kacper.misterski.rangestats.feature.onboarding.domain.repository.OnboardingRepository

class CompleteOnboardingUseCase(private val onboardingRepository: OnboardingRepository) {
    suspend operator fun invoke(profile: UserProfile) = onboardingRepository.completeOnboarding(profile)
}
