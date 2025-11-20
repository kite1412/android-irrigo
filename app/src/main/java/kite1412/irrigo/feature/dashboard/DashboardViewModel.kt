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
import kite1412.irrigo.domain.LightIntensityLogRepository
import kite1412.irrigo.domain.SoilMoistureLogRepository
import kite1412.irrigo.domain.WaterCapacityRepository
import kite1412.irrigo.domain.WateringRepository
import kite1412.irrigo.feature.dashboard.util.DashboardUiEvent
import kite1412.irrigo.model.Device
import kite1412.irrigo.model.LightIntensityLog
import kite1412.irrigo.model.SoilMoistureLog
import kite1412.irrigo.model.WaterCapacityLog
import kite1412.irrigo.model.WaterContainer
import kite1412.irrigo.model.WateringConfig
import kite1412.irrigo.model.WateringLog
import kite1412.irrigo.util.IntPreferencesKey
import kite1412.irrigo.util.getPreference
import kite1412.irrigo.util.updatePreferences
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DashboardViewModel @Inject constructor(
    @param:ApplicationContext private val context: Context,
    private val deviceRepository: DeviceRepository,
    private val waterCapacityRepository: WaterCapacityRepository,
    private val wateringRepository: WateringRepository,
    private val soilMoistureLogRepository: SoilMoistureLogRepository,
    private val lightIntensityLogRepository: LightIntensityLogRepository
) : ViewModel() {
    var device by mutableStateOf<Device?>(null)
        private set
    var latestWaterCapacityLog by mutableStateOf<WaterCapacityLog?>(null)
        private set
    var waterContainer by mutableStateOf<WaterContainer?>(null)
        private set
    var latestSoilMoistureLog by mutableStateOf<SoilMoistureLog?>(null)
        private set
    var latestLightIntensityLog by mutableStateOf<LightIntensityLog?>(null)
        private set
    val latestWateringLogs = mutableStateListOf<WateringLog>()
    var wateringConfig by mutableStateOf<WateringConfig?>(null)

    private val _uiEvent = MutableSharedFlow<DashboardUiEvent>()
    val uiEvent = _uiEvent.asSharedFlow()

    val devices = mutableStateListOf<Device>()
    val realtimeJobs = mutableListOf<Job>()

    init {
        viewModelScope.launch {
            val devices = deviceRepository.getDevices()
            this@DashboardViewModel.devices.addAll(devices)
            val device = devices.firstOrNull {
                it.id == (context
                    .getPreference(IntPreferencesKey.SELECTED_DEVICE_ID, 1) ?: 1)
            } ?: devices.firstOrNull()?.also {
                context.updatePreferences(
                    key = IntPreferencesKey.SELECTED_DEVICE_ID,
                    value = it.id
                )
            }
            wateringConfig = wateringRepository.getConfig()
            if (device == null) {
                _uiEvent.emit(DashboardUiEvent.ShowSnackbar("Device not found"))
                return@launch
            } else updateDeviceInfo(newDevice = device)
        }
    }

    private fun updateDeviceInfo(newDevice: Device) {
        viewModelScope.launch {
            realtimeJobs.forEach {
                it.cancel()
            }
            realtimeJobs.clear()
            this@DashboardViewModel.device = newDevice
            waterContainer = deviceRepository.getWaterContainer(newDevice.id)
            realtimeJobs.add(
                launch {
                    latestWaterCapacityLog = waterCapacityRepository
                        .getWaterCapacityLogs(newDevice.id)
                        .maxByOrNull { it.timestamp }
                    waterCapacityRepository
                        .getLatestWaterCapacityLogFlow(newDevice.id)
                        .collect {
                            latestWaterCapacityLog = it
                        }
                }
            )
            realtimeJobs.add(
                launch {
                    latestLightIntensityLog = lightIntensityLogRepository
                        .getLightIntensityLogs(newDevice.id)
                        .maxByOrNull { it.timestamp }
                    lightIntensityLogRepository
                        .getLatestLightIntensityLog(newDevice.id)
                        .collect {
                            latestLightIntensityLog = it
                        }
                }
            )
            latestWateringLogs.clear()
            latestWateringLogs.addAll(
                wateringRepository.getWateringLogs(newDevice.id)
                    .sortedByDescending { it.timestamp }
            )
            realtimeJobs.add(
                launch {
                    wateringRepository
                        .getLatestWateringLog(newDevice.id)
                        .collect {
                            latestWateringLogs.add(
                                index = 0,
                                element = it
                            )
                        }
                }
            )
            realtimeJobs.add(
                launch {
                    latestSoilMoistureLog = soilMoistureLogRepository
                        .getSoilMoistureLogs(newDevice.id)
                        .maxByOrNull { it.timestamp }
                    soilMoistureLogRepository.getLatestSoilMoistureLog(newDevice.id)
                        .collect {
                            latestSoilMoistureLog = it
                        }
                }
            )
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

    fun sendWateringSignal() {
        viewModelScope.launch {
            device?.id?.let {
                deviceRepository.sendWateringSignal(it)
                _uiEvent.emit(DashboardUiEvent.ShowSnackbar("Penyiraman dilakukan"))
            }
        }
    }
}