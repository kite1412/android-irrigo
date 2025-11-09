package kite1412.irrigo.feature.dashboard

import android.content.Context
import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kite1412.irrigo.domain.DeviceRepository
import kite1412.irrigo.domain.SoilMoistureLogRepository
import kite1412.irrigo.domain.WaterCapacityLogRepository
import kite1412.irrigo.domain.WateringRepository
import kite1412.irrigo.feature.dashboard.util.DashboardUiEvent
import kite1412.irrigo.model.Device
import kite1412.irrigo.model.SoilMoistureLog
import kite1412.irrigo.model.WaterCapacityLog
import kite1412.irrigo.model.WaterContainer
import kite1412.irrigo.model.WateringConfig
import kite1412.irrigo.model.WateringLog
import kite1412.irrigo.util.IntPreferencesKey
import kite1412.irrigo.util.getPreference
import kite1412.irrigo.util.updatePreferences
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.emptyFlow
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DashboardViewModel @Inject constructor(
    @param:ApplicationContext private val context: Context,
    private val deviceRepository: DeviceRepository,
    private val waterCapacityLogRepository: WaterCapacityLogRepository,
    private val wateringRepository: WateringRepository,
    private val soilMoistureLogRepository: SoilMoistureLogRepository
) : ViewModel() {
    var device by mutableStateOf<Device?>(null)
        private set
    var latestWaterCapacityLog = emptyFlow<WaterCapacityLog>()
        private set
    var waterContainer by mutableStateOf<WaterContainer?>(null)
        private set
    var latestWateringLogs = emptyFlow<List<WateringLog>>()
        private set
    var latestSoilMoistureLog = emptyFlow<SoilMoistureLog>()
        private set
    var wateringConfig by mutableStateOf<WateringConfig?>(null)
    private val _uiEvent = MutableSharedFlow<DashboardUiEvent>()
    val uiEvent = _uiEvent.asSharedFlow()

    val devices = mutableStateListOf<Device>()

    init {
        viewModelScope.launch {
            val device = deviceRepository.getDeviceById(
                id = context
                    .getPreference(IntPreferencesKey.SELECTED_DEVICE_ID) ?: 1
            )
            wateringConfig = wateringRepository.getConfig()
            if (device == null) {
                _uiEvent.emit(DashboardUiEvent.ShowSnackbar("Device not found"))
                return@launch
            } else updateDeviceInfo(newDevice = device)

            devices.addAll(deviceRepository.getDevices())
        }
    }

    fun onDeviceChange(device: Device) {
        viewModelScope.launch {
            updateDeviceInfo(device)
            context.updatePreferences(
                key = IntPreferencesKey.SELECTED_DEVICE_ID,
                value = device.id
            )
        }
    }

    private fun updateDeviceInfo(newDevice: Device) {
        viewModelScope.launch {
            this@DashboardViewModel.device = newDevice
            waterContainer = deviceRepository.getWaterContainer(newDevice.id)
            latestWaterCapacityLog = waterCapacityLogRepository
                .getLatestLogFlow(newDevice.id)
                .onEach {
                    Log.d(
                        "DashboardViewModel",
                        "deviceId: ${newDevice.id}, latest water cap log: ${it.currentHeightCm}cm"
                    )
                }
            latestWateringLogs = wateringRepository.getWateringLogsFlow(newDevice.id)
            latestSoilMoistureLog = soilMoistureLogRepository.getLatestSoilMoistureLog(newDevice.id)
        }
    }
}