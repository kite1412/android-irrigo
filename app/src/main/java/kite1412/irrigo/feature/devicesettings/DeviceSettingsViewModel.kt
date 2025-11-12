package kite1412.irrigo.feature.devicesettings

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kite1412.irrigo.domain.WaterCapacityRepository
import kite1412.irrigo.domain.WateringRepository
import kite1412.irrigo.model.WaterCapacityConfig
import kite1412.irrigo.model.WateringConfig
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DeviceSettingsViewModel @Inject constructor(
    private val wateringRepository: WateringRepository,
    private val waterCapacityRepository: WaterCapacityRepository
) : ViewModel() {
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
            isAutomated = wConfig.automated
            wateringConfig = wConfig
            waterCapacityConfig = waterCapacityRepository.getConfig()
        }
    }

    fun updateAutomatedSetting(new: Boolean) {
        isAutomated = new
    }

    fun updateWateringReminder(value: Boolean) {
        wateringReminder = value
    }

    fun updateWaterCapacityReminder(value: Boolean) {
        waterCapacityReminder = value
    }

    fun updateMinSoilMoisture(value: Float) {
        wateringConfig = wateringConfig?.copy(
            minSoilMoisturePercent = value
        )
    }

    fun updateWateringDuration(ms: Int) {
        wateringConfig = wateringConfig?.copy(
            durationMs = ms
        )
    }

    fun updateMinWaterCapacity(value: Float) {
        waterCapacityConfig = waterCapacityConfig?.copy(
            minWaterCapacityPercent = value
        )
    }
}