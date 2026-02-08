package pl.kacper.misterski.rangestats.di

import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module
import pl.kacper.misterski.rangestats.ui.login.LoginViewModel
import pl.kacper.misterski.rangestats.ui.main.MainViewModel

val viewModelModule = module {
    viewModelOf(::LoginViewModel)
    viewModelOf(::MainViewModel)
}