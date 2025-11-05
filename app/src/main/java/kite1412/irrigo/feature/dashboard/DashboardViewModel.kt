package kite1412.irrigo.feature.dashboard

import android.content.Context
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kite1412.irrigo.domain.DeviceRepository
import kite1412.irrigo.model.Device
import kite1412.irrigo.util.IntPreferencesKey
import kite1412.irrigo.util.getPreference
import kite1412.irrigo.util.updatePreferences
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DashboardViewModel @Inject constructor(
    @param:ApplicationContext private val context: Context,
    private val deviceRepository: DeviceRepository
) : ViewModel() {
    var device by mutableStateOf<Device?>(null)
        private set
    val devices = mutableStateListOf<Device>()

    init {
        viewModelScope.launch {
            device = deviceRepository.getDeviceById(
                id = context
                    .getPreference(IntPreferencesKey.SELECTED_DEVICE_ID) ?: 1
            )
            devices.addAll(deviceRepository.getDevices())
        }
    }

    fun onDeviceChange(device: Device) {
        viewModelScope.launch {
            this@DashboardViewModel.device = device
            context.updatePreferences(
                key = IntPreferencesKey.SELECTED_DEVICE_ID,
                value = device.id
            )
        }
    }
}