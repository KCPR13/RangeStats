package pl.kacper.misterski.rangestats.feature.session.di

import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module
import pl.kacper.misterski.rangestats.feature.session.data.repository.SessionRepositoryImpl
import pl.kacper.misterski.rangestats.feature.session.data.repository.WeaponLookupRepositoryImpl
import pl.kacper.misterski.rangestats.feature.session.domain.repository.SessionRepository
import pl.kacper.misterski.rangestats.feature.session.domain.repository.WeaponLookupRepository
import pl.kacper.misterski.rangestats.feature.session.domain.usecase.AddShotUseCase
import pl.kacper.misterski.rangestats.feature.session.domain.usecase.AnalyzeTargetUseCase
import pl.kacper.misterski.rangestats.feature.session.domain.usecase.FinishSessionUseCase
import pl.kacper.misterski.rangestats.feature.session.domain.usecase.GetSessionUseCase
import pl.kacper.misterski.rangestats.feature.session.domain.usecase.GetWeaponByNameUseCase
import pl.kacper.misterski.rangestats.feature.session.domain.usecase.StartSessionUseCase
import pl.kacper.misterski.rangestats.feature.session.ui.active.ActiveSessionViewModel
import pl.kacper.misterski.rangestats.feature.session.ui.dashboard.DashboardViewModel
import pl.kacper.misterski.rangestats.feature.session.ui.new.NewSessionViewModel
import pl.kacper.misterski.rangestats.feature.session.ui.summary.SessionSummaryViewModel

val sessionModule = module {
    viewModelOf(::DashboardViewModel)
    viewModelOf(::NewSessionViewModel)
    viewModelOf(::ActiveSessionViewModel)
    viewModelOf(::SessionSummaryViewModel)
    factory { StartSessionUseCase(get()) }
    factory { AddShotUseCase(get()) }
    factory { AnalyzeTargetUseCase(get()) }
    factory { FinishSessionUseCase(get()) }
    factory { GetSessionUseCase(get()) }
    factory { GetWeaponByNameUseCase(get()) }
    single<SessionRepository> { SessionRepositoryImpl(get(), get()) }
    single<WeaponLookupRepository> { WeaponLookupRepositoryImpl(get()) }
}
