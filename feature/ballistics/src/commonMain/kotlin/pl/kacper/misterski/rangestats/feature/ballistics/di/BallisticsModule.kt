package pl.kacper.misterski.rangestats.feature.ballistics.di

import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module
import pl.kacper.misterski.rangestats.core.domain.di.ioDispatcherQualifier
import pl.kacper.misterski.rangestats.feature.ballistics.data.repository.BallisticsRepositoryImpl
import pl.kacper.misterski.rangestats.feature.ballistics.domain.repository.BallisticsRepository
import pl.kacper.misterski.rangestats.feature.ballistics.domain.usecase.CalculateTrajectoryUseCase
import pl.kacper.misterski.rangestats.feature.ballistics.domain.usecase.GetCaliberPresetsUseCase
import pl.kacper.misterski.rangestats.feature.ballistics.domain.validator.BallisticsInputValidator
import pl.kacper.misterski.rangestats.feature.ballistics.ui.calculator.BallisticsViewModel

val ballisticsModule = module {
    viewModelOf(::BallisticsViewModel)
    factory { CalculateTrajectoryUseCase(get(ioDispatcherQualifier)) }
    factory { GetCaliberPresetsUseCase(get(), get(ioDispatcherQualifier)) }
    factory { BallisticsInputValidator() }
    single<BallisticsRepository> { BallisticsRepositoryImpl() }
}
