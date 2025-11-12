package kite1412.irrigo.feature.devicesettings

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kite1412.irrigo.domain.WateringRepository
import javax.inject.Inject

@HiltViewModel
class DeviceSettingsViewModel @Inject constructor(
    private val wateringRepository: WateringRepository
) : ViewModel() {
    var isAutomated by mutableStateOf(false)
        private set
    var wateringReminder by mutableStateOf(false)
        private set
    var waterCapacityReminder by mutableStateOf(false)
        private set

    fun updateAutomatedSetting(new: Boolean) {
        isAutomated = new
    }

    fun updateWateringReminder(value: Boolean) {
        wateringReminder = value
    }

    fun updateWaterCapacityReminder(value: Boolean) {
        waterCapacityReminder = value
    }
}