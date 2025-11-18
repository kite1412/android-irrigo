package kite1412.irrigo.feature.devicesettings

import android.content.Context
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kite1412.irrigo.domain.WaterCapacityRepository
import kite1412.irrigo.domain.WateringRepository
import kite1412.irrigo.feature.devicesettings.navigation.DeviceSettingsRoute
import kite1412.irrigo.feature.devicesettings.util.Setting
import kite1412.irrigo.model.WaterCapacityConfig
import kite1412.irrigo.model.WateringConfig
import kite1412.irrigo.util.BooleanPreferencesKey
import kite1412.irrigo.util.getPreference
import kite1412.irrigo.util.updatePreferences
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DeviceSettingsViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    @param:ApplicationContext private val context: Context,
    private val wateringRepository: WateringRepository,
    private val waterCapacityRepository: WaterCapacityRepository
) : ViewModel() {
    val highlightedSetting = savedStateHandle
        .toRoute<DeviceSettingsRoute>()
        .highlightSettingOrdinal
        ?.let(Setting.entries::get)
    var isAutomated by mutableStateOf(false)
        private set
    var wateringReminder by mutableStateOf(true)
        private set
    var waterCapacityReminder by mutableStateOf(true)
        private set
    var wateringConfig by mutableStateOf<WateringConfig?>(null)
        private set
    var waterCapacityConfig by mutableStateOf<WaterCapacityConfig?>(null)
        private set

    init {
        viewModelScope.launch {
            val wConfig = wateringRepository.getConfig()
            isAutomated = wConfig?.automated ?: false
            wateringReminder = context.getPreference(
                key = BooleanPreferencesKey.WATERING_REMINDER_ENABLED,
                defaultValue = true
            ) ?: true
            waterCapacityReminder = context.getPreference(
                key = BooleanPreferencesKey.WATER_CAPACITY_REMINDER_ENABLED,
                defaultValue = true
            ) ?: true
            wateringConfig = wConfig
            waterCapacityConfig = waterCapacityRepository.getConfig()
        }
    }

    private fun updateWateringConfig() {
        wateringConfig?.let {
            viewModelScope.launch {
                wateringRepository.updateConfig(it)
            }
        }
    }

    private fun updateWaterCapacityConfig() {
        waterCapacityConfig?.let {
            viewModelScope.launch {
                waterCapacityRepository.updateConfig(it)
            }
        }
    }

    fun updateAutomatedSetting(new: Boolean) {
        isAutomated = new
    }

    fun updateWateringReminder(value: Boolean) {
        wateringReminder = value
        viewModelScope.launch {
            context.updatePreferences(
                key = BooleanPreferencesKey.WATERING_REMINDER_ENABLED,
                value = value
            )
        }
    }

    fun updateWaterCapacityReminder(value: Boolean) {
        waterCapacityReminder = value
        viewModelScope.launch {
            context.updatePreferences(
                key = BooleanPreferencesKey.WATER_CAPACITY_REMINDER_ENABLED,
                value = value
            )
        }
    }

    fun updateMinSoilMoisture(value: Float) {
        wateringConfig = wateringConfig?.copy(
            minSoilMoisturePercent = value
        )
        updateWateringConfig()
    }

    fun updateWateringDuration(ms: Int) {
        wateringConfig = wateringConfig?.copy(
            durationMs = ms
        )
        updateWateringConfig()
    }

    fun updateMinWaterCapacity(value: Double) {
        waterCapacityConfig = waterCapacityConfig?.copy(
            minWaterCapacityPercent = value
        )
        updateWaterCapacityConfig()
    }
}