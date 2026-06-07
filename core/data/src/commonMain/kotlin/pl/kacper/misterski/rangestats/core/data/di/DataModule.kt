package pl.kacper.misterski.rangestats.core.data.di

import androidx.sqlite.driver.bundled.BundledSQLiteDriver
import org.koin.dsl.module
import pl.kacper.misterski.rangestats.core.data.datasource.permission.PermissionDataSource
import pl.kacper.misterski.rangestats.core.data.datasource.permission.createPermissionDataSource
import pl.kacper.misterski.rangestats.core.data.datasource.session.SessionDataSource
import pl.kacper.misterski.rangestats.core.data.datasource.userprefs.UserPrefsDataSource
import pl.kacper.misterski.rangestats.core.data.datasource.vision.VisionDataSource
import pl.kacper.misterski.rangestats.core.data.datasource.weapon.WeaponDataSource
import pl.kacper.misterski.rangestats.core.data.datasource.session.SessionDataSourceImpl
import pl.kacper.misterski.rangestats.core.data.datasource.vision.VisionDataSourceImpl
import pl.kacper.misterski.rangestats.core.data.datasource.weapon.WeaponDataSourceImpl
import pl.kacper.misterski.rangestats.core.data.datastore.UserPrefsDataSourceImpl
import pl.kacper.misterski.rangestats.core.data.datastore.createDataStore
import pl.kacper.misterski.rangestats.core.data.database.AppDatabase
import pl.kacper.misterski.rangestats.core.data.remote.AnthropicVisionApiService
import pl.kacper.misterski.rangestats.core.data.database.getDatabaseBuilder
import pl.kacper.misterski.rangestats.core.data.ktor.HttpClientFactory

val dataModule = module {
    single<AppDatabase> {
        getDatabaseBuilder()
            .setDriver(BundledSQLiteDriver())
            .build()
    }
    single<SessionDataSource> { SessionDataSourceImpl(get<AppDatabase>().sessionDao(),
        get<AppDatabase>().shotDao()) }
    single<WeaponDataSource> { WeaponDataSourceImpl(get<AppDatabase>().weaponDao()) }
    single<VisionDataSource> { VisionDataSourceImpl(get()) }
    single<UserPrefsDataSource> { UserPrefsDataSourceImpl(createDataStore()) }
    single<PermissionDataSource> { createPermissionDataSource() }
    single { HttpClientFactory.create() }
    single { AnthropicVisionApiService(get(), getProperty("anthropic.api.key", "")) }
    // TODO add key
}
