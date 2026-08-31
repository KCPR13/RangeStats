package pl.kacper.misterski.rangestats.core.navigation.di

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.MainScope
import org.koin.core.qualifier.named
import org.koin.dsl.module
import pl.kacper.misterski.rangestats.core.navigation.Navigator
import pl.kacper.misterski.rangestats.core.navigation.NavigatorImpl

val navigatorScopeQualifier = named("navigatorScope")

val navigationModule =
    module {
        single<CoroutineScope>(navigatorScopeQualifier) { MainScope() }
        single<Navigator> { NavigatorImpl(get(navigatorScopeQualifier)) }
    }
