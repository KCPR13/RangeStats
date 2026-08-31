package pl.kacper.misterski.rangestats.feature.settings.di

import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module
import pl.kacper.misterski.rangestats.core.domain.di.ioDispatcherQualifier
import pl.kacper.misterski.rangestats.core.domain.converter.UnitConverter
import pl.kacper.misterski.rangestats.feature.settings.data.repository.ProfileRepositoryImpl
import pl.kacper.misterski.rangestats.feature.settings.domain.repository.ProfileRepository
import pl.kacper.misterski.rangestats.feature.settings.domain.usecase.GetUserProfileUseCase
import pl.kacper.misterski.rangestats.feature.settings.domain.usecase.UpdateUserProfileUseCase
import pl.kacper.misterski.rangestats.feature.settings.ui.WeaponAddedNotifier
import pl.kacper.misterski.rangestats.feature.settings.ui.settings.SettingsViewModel
import pl.kacper.misterski.rangestats.feature.settings.ui.weapon.add.AddWeaponViewModel
val settingsModule = module {
    viewModelOf(::SettingsViewModel)
    viewModelOf(::AddWeaponViewModel)
    factory { GetUserProfileUseCase(get(), get(ioDispatcherQualifier)) }
    factory { UpdateUserProfileUseCase(get(), get(ioDispatcherQualifier)) }
    single { UnitConverter() }
    single { WeaponAddedNotifier() }
    single<ProfileRepository> { ProfileRepositoryImpl(get()) }
}
