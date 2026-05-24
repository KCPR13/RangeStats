package pl.kacper.misterski.rangestats.feature.history.di

import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module
import pl.kacper.misterski.rangestats.feature.history.data.repository.HistoryRepositoryImpl
import pl.kacper.misterski.rangestats.feature.history.domain.repository.HistoryRepository
import pl.kacper.misterski.rangestats.feature.history.domain.usecase.DeleteSessionUseCase
import pl.kacper.misterski.rangestats.feature.history.domain.usecase.GetSessionDetailUseCase
import pl.kacper.misterski.rangestats.feature.history.domain.usecase.GetSessionsUseCase
import pl.kacper.misterski.rangestats.feature.history.ui.detail.SessionDetailViewModel
import pl.kacper.misterski.rangestats.feature.history.ui.list.HistoryViewModel

val historyModule = module {
    viewModelOf(::HistoryViewModel)
    viewModelOf(::SessionDetailViewModel)
    factory { GetSessionsUseCase(get()) }
    factory { GetSessionDetailUseCase(get()) }
    factory { DeleteSessionUseCase(get()) }
    single<HistoryRepository> { HistoryRepositoryImpl(get()) }
}
