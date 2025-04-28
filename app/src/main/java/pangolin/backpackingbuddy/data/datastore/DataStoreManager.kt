package pangolin.backpackingbuddy.data.datastore

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class DataStoreManager(private val context: Context) {
    companion object {
        private const val DATA_STORE_NAME = "preferences"
        private val Context.dataStore: DataStore<Preferences>
        by preferencesDataStore(name = DATA_STORE_NAME)
        private val MAPTYPE_KEY = stringPreferencesKey("maptype_key")
        private val LOCATION_KEY = booleanPreferencesKey("location_key")
    }

    val maptypeFlow: Flow<String> = context.dataStore.data.map { preferences ->
        preferences[MAPTYPE_KEY] ?: "NORMAL"
    }

    val locationFlow: Flow<Boolean> = context.dataStore.data.map { preferences ->
        preferences[LOCATION_KEY] ?: false
    }

    suspend fun setMaptype(newMaptype: String) {
        context.dataStore.edit { preferences ->
            preferences[MAPTYPE_KEY] = newMaptype
        }
    }

    suspend fun setLocation(newLocation: Boolean) {
        context.dataStore.edit { preferences ->
            preferences[LOCATION_KEY] = newLocation
        }
    }
}