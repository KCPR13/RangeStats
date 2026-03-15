package pl.kacper.misterski.rangestats.core.data.datastore

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import org.koin.mp.KoinPlatform

actual fun createDataStore(): DataStore<Preferences> {
    val context: Context = KoinPlatform.getKoin().get()
    val path = context.filesDir.resolve(USER_PREFS_FILENAME).absolutePath
    return createDataStoreWithPath(path)
}
