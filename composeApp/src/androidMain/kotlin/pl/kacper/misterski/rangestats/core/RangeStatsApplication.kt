package pl.kacper.misterski.rangestats.core

import android.app.Application
import org.koin.android.ext.koin.androidContext
import pl.kacper.misterski.rangestats.di.initializeKoin


class RangeStatsApplication: Application() {

    override fun onCreate() {
        super.onCreate()
        initializeKoin {
            androidContext(this@RangeStatsApplication)
        }
    }
}