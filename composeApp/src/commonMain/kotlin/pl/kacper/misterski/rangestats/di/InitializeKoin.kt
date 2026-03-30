package pl.kacper.misterski.rangestats.di

import org.koin.core.context.startKoin
import org.koin.dsl.KoinAppDeclaration
import pl.kacper.misterski.rangestats.core.data.di.dataModule
import pl.kacper.misterski.rangestats.feature.onboarding.di.onboardingModule
import pl.kacper.misterski.rangestats.feature.settings.di.settingsModule

fun initializeKoin(config: KoinAppDeclaration? = null) {
    startKoin {
        config?.invoke(this)
        modules(
            dataModule,
            onboardingModule,
            settingsModule
        )
    }
}
