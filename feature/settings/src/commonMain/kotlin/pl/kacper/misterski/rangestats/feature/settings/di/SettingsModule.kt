package pl.kacper.misterski.rangestats.feature.settings.di

import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module
import pl.kacper.misterski.rangestats.core.data.di.ioDispatcherQualifier
import pl.kacper.misterski.rangestats.core.domain.converter.UnitConverter
import pl.kacper.misterski.rangestats.feature.settings.data.repository.ProfileRepositoryImpl
import pl.kacper.misterski.rangestats.feature.settings.data.repository.WeaponRepositoryImpl
import pl.kacper.misterski.rangestats.feature.settings.domain.repository.ProfileRepository
import pl.kacper.misterski.rangestats.feature.settings.domain.repository.WeaponRepository
import pl.kacper.misterski.rangestats.feature.settings.domain.usecase.AddWeaponUseCase
import pl.kacper.misterski.rangestats.feature.settings.domain.usecase.DeleteWeaponUseCase
import pl.kacper.misterski.rangestats.feature.settings.domain.usecase.GetUserProfileUseCase
import pl.kacper.misterski.rangestats.feature.settings.domain.usecase.GetWeaponsUseCase
import pl.kacper.misterski.rangestats.feature.settings.domain.usecase.UpdateUserProfileUseCase
import pl.kacper.misterski.rangestats.feature.settings.ui.settings.SettingsViewModel
import pl.kacper.misterski.rangestats.feature.settings.ui.weapon.add.AddWeaponViewModel
val settingsModule = module {
    viewModelOf(::SettingsViewModel)
    viewModelOf(::AddWeaponViewModel)
    factory { GetWeaponsUseCase(get(), get(ioDispatcherQualifier)) }
    factory { AddWeaponUseCase(get(), get(ioDispatcherQualifier)) }
    factory { DeleteWeaponUseCase(get(), get(ioDispatcherQualifier)) }
    factory { GetUserProfileUseCase(get(), get(ioDispatcherQualifier)) }
    factory { UpdateUserProfileUseCase(get(), get(ioDispatcherQualifier)) }
    single { UnitConverter() }
    single<WeaponRepository> { WeaponRepositoryImpl(get()) }
    single<ProfileRepository> { ProfileRepositoryImpl(get()) }
}
