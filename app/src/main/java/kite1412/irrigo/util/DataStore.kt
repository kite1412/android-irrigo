package kite1412.irrigo.util

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.first

val Context.dataStore: DataStore<Preferences> by preferencesDataStore("preferences")

suspend fun <T> Context.updatePreferences(
    key: Preferences.Key<T>,
    value: T
): Preferences = dataStore.edit {
    it[key] = value
}

suspend fun <T> Context.getPreference(
    key: Preferences.Key<T>,
    defaultValue: T? = null
): T? {
    val value = dataStore.data.first()[key]
    if (value == null) {
        if (defaultValue != null) updatePreferences(key, defaultValue)
        return defaultValue
    }
    return value
}