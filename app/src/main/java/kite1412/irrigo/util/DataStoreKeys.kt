package kite1412.irrigo.util

import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.intPreferencesKey

object IntPreferencesKey {
    val SELECTED_DEVICE_ID = intPreferencesKey("selected_device_id")
}

object BooleanPreferencesKey {
    val WATERING_REMINDER_ENABLED = booleanPreferencesKey("watering_reminder_enabled")
    val WATER_CAPACITY_REMINDER_ENABLED = booleanPreferencesKey("water_capacity_reminder_enabled")
}