package pl.kacper.misterski.rangestats.androidapp.core

import android.app.Application
import android.os.Build
import org.koin.android.ext.koin.androidContext
import pl.kacper.misterski.rangestats.di.initializeKoin

//1.  screenOrientation nie działa na dużych ekranach
//2. static final przez reflection
//3. Charles / nasłuchiwanie proxy przestaje działąć w network_security_config trzeba dodać <certificateTransparency enabled="false">
//4. Klawiatura po rotacji się ukrywa, fix to windowSoftInputMode="stateAlwaysVisible"
//5. recreate Activity się zmienia, do testów
class RangeStatsApplication: Application() {

    override fun onCreate() {
        super.onCreate()
        initializeKoin {
            androidContext(this@RangeStatsApplication)
        }
        //reflection()
    }

    private fun reflection(){ // public static final
        val field = Build.VERSION::class.java.getDeclaredField("SDK_INT")
        field.isAccessible = true
        field.set(null, 99)
    }
}