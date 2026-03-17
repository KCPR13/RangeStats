package pl.kacper.misterski.rangestats.feature.onboarding.di

import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module
import pl.kacper.misterski.rangestats.feature.onboarding.data.repository.OnboardingRepositoryImpl
import pl.kacper.misterski.rangestats.feature.onboarding.domain.repository.OnboardingRepository
import pl.kacper.misterski.rangestats.feature.onboarding.domain.usecase.CompleteOnboardingUseCase
import pl.kacper.misterski.rangestats.feature.onboarding.domain.usecase.IsOnboardingCompletedUseCase
import pl.kacper.misterski.rangestats.feature.onboarding.ui.OnboardingViewModel

val onboardingModule = module {
    viewModelOf(::OnboardingViewModel)
    factory { IsOnboardingCompletedUseCase(get()) }
    factory { CompleteOnboardingUseCase(get()) }
    single<OnboardingRepository> { OnboardingRepositoryImpl(get()) }
}
