package pl.kacper.misterski.rangestats.core.data.datastore

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import kotlinx.coroutines.flow.first
import pl.kacper.misterski.rangestats.core.data.datasource.userprefs.UserPrefsDataSource
import pl.kacper.misterski.rangestats.core.domain.enums.UnitSystem
import pl.kacper.misterski.rangestats.core.domain.models.UserProfile

class UserPrefsDataSourceImpl(
    private val dataStore: DataStore<Preferences>,
) : UserPrefsDataSource {

    @Throws(Exception::class)
    override suspend fun getUserProfile(): UserProfile =
        dataStore.data.first().let { prefs ->
            UserProfile(
                displayName = prefs[KEY_DISPLAY_NAME].orEmpty(),
                units = prefs[KEY_UNIT_SYSTEM]
                    ?.let { runCatching { UnitSystem.valueOf(it) }.getOrNull() }
                    ?: UnitSystem.METRIC,
                defaultDistanceMeters = prefs[KEY_DEFAULT_DISTANCE] ?: DEFAULT_DISTANCE_METERS,
            )
        }

    override suspend fun updateUserProfile(profile: UserProfile) {
        dataStore.edit { prefs ->
            prefs[KEY_DISPLAY_NAME] = profile.displayName
            prefs[KEY_UNIT_SYSTEM] = profile.units.name
            prefs[KEY_DEFAULT_DISTANCE] = profile.defaultDistanceMeters
        }
    }

    override suspend fun isOnboardingCompleted(): Boolean =
        dataStore.data.first()[KEY_ONBOARDING_COMPLETED] ?: false

    override suspend fun setOnboardingCompleted() {
        dataStore.edit { prefs -> prefs[KEY_ONBOARDING_COMPLETED] = true }
    }

    companion object {
        private val KEY_DISPLAY_NAME = stringPreferencesKey("display_name")
        private val KEY_UNIT_SYSTEM = stringPreferencesKey("unit_system")
        private val KEY_DEFAULT_DISTANCE = intPreferencesKey("default_distance_meters")
        private val KEY_ONBOARDING_COMPLETED = booleanPreferencesKey("onboarding_completed")

        private const val DEFAULT_DISTANCE_METERS = 25
    }
}
