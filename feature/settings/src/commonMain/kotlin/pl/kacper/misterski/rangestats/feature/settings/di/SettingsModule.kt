package pl.kacper.misterski.rangestats.feature.settings.di

import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module
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
import pl.kacper.misterski.rangestats.feature.settings.ui.weapon.WeaponListViewModel
import pl.kacper.misterski.rangestats.feature.settings.ui.weapon.add.AddWeaponViewModel
val settingsModule = module {
    viewModelOf(::SettingsViewModel)
    viewModelOf(::WeaponListViewModel)
    viewModelOf(::AddWeaponViewModel)
    factory { GetWeaponsUseCase(get()) }
    factory { AddWeaponUseCase(get()) }
    factory { DeleteWeaponUseCase(get()) }
    factory { GetUserProfileUseCase(get()) }
    factory { UpdateUserProfileUseCase(get()) }
    single<WeaponRepository> { WeaponRepositoryImpl(get()) }
    single<ProfileRepository> { ProfileRepositoryImpl(get()) }
}
