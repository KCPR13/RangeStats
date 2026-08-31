package pl.kacper.misterski.rangestats.di

import org.koin.core.context.startKoin
import org.koin.dsl.KoinAppDeclaration
import pl.kacper.misterski.rangestats.core.data.di.dataModule
import pl.kacper.misterski.rangestats.core.domain.di.dispatcherModule
import pl.kacper.misterski.rangestats.core.domain.di.useCaseModule
import pl.kacper.misterski.rangestats.core.navigation.di.navigationModule
import pl.kacper.misterski.rangestats.feature.ballistics.di.ballisticsModule
import pl.kacper.misterski.rangestats.feature.history.di.historyModule
import pl.kacper.misterski.rangestats.feature.onboarding.di.onboardingModule
import pl.kacper.misterski.rangestats.feature.session.di.sessionModule
import pl.kacper.misterski.rangestats.feature.settings.di.settingsModule

fun initializeKoin(config: KoinAppDeclaration? = null) {
    startKoin {
        config?.invoke(this)
        modules(
            dataModule,
            dispatcherModule,
            useCaseModule,
            navigationModule,
            appModule,
            onboardingModule,
            settingsModule,
            sessionModule,
            historyModule,
            ballisticsModule,
        )
    }
}
