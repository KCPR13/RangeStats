package pl.kacper.misterski.rangestats.di

import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module
import pl.kacper.misterski.rangestats.core.AppViewModel

val appModule = module {
    viewModelOf(::AppViewModel)
}
