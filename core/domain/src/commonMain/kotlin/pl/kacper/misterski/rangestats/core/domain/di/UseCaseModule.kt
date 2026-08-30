package pl.kacper.misterski.rangestats.core.domain.di

import org.koin.dsl.module
import pl.kacper.misterski.rangestats.core.domain.usecase.AddWeaponUseCase
import pl.kacper.misterski.rangestats.core.domain.usecase.DeleteWeaponUseCase
import pl.kacper.misterski.rangestats.core.domain.usecase.GetWeaponByNameUseCase
import pl.kacper.misterski.rangestats.core.domain.usecase.GetWeaponsUseCase

val useCaseModule =
    module {
        factory { GetWeaponsUseCase(get(), get(ioDispatcherQualifier)) }
        factory { GetWeaponByNameUseCase(get(), get(ioDispatcherQualifier)) }
        factory { AddWeaponUseCase(get(), get(ioDispatcherQualifier)) }
        factory { DeleteWeaponUseCase(get(), get(ioDispatcherQualifier)) }
    }
