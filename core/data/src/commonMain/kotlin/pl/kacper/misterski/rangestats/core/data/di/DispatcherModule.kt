package pl.kacper.misterski.rangestats.core.data.di

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import org.koin.core.qualifier.named
import org.koin.dsl.module

val ioDispatcherQualifier = named("ioDispatcher")

val dispatcherModule = module {
    single<CoroutineDispatcher>(ioDispatcherQualifier) { Dispatchers.IO }
}