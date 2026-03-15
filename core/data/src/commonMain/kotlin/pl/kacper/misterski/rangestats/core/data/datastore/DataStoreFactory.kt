package pl.kacper.misterski.rangestats.core.data.datastore

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.PreferenceDataStoreFactory
import androidx.datastore.preferences.core.Preferences
import okio.Path.Companion.toPath

internal const val USER_PREFS_FILENAME = "user_prefs.preferences_pb"

internal fun createDataStoreWithPath(path: String): DataStore<Preferences> =
    PreferenceDataStoreFactory.createWithPath(
        produceFile = { path.toPath() },
    )

expect fun createDataStore(): DataStore<Preferences>
