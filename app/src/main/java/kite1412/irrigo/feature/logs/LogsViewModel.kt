package kite1412.irrigo.feature.logs

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
import kite1412.irrigo.domain.SoilMoistureLogRepository
import kite1412.irrigo.domain.WateringRepository
import kite1412.irrigo.feature.logs.util.LogsGroupType
import kite1412.irrigo.model.Device
import kite1412.irrigo.model.SoilMoistureLog
import kite1412.irrigo.model.WateringLog
import kite1412.irrigo.util.IntPreferencesKey
import kite1412.irrigo.util.getPreference
import kite1412.irrigo.util.toLocalDateTime
import kite1412.irrigo.util.updatePreferences
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.time.Instant

@HiltViewModel
class LogsViewModel @Inject constructor(
    @param:ApplicationContext private val context: Context,
    private val soilMoistureLogRepository: SoilMoistureLogRepository,
    private val deviceRepository: DeviceRepository,
    private val wateringRepository: WateringRepository
) : ViewModel() {
    var device by mutableStateOf<Device?>(null)
        private set
    var selectedLogsGroup by mutableStateOf<LogsGroupType?>(null)
        private set
    var fetchingLogs by mutableStateOf(false)
        private set
    var selectedDate by mutableStateOf<Instant?>(null)
        private set
    val soilMoistureLogs = mutableStateListOf<SoilMoistureLog>()
    val wateringLogs = mutableStateListOf<WateringLog>()
    val availableDates = mutableStateListOf<Instant>()
    val devices = mutableStateListOf<Device>()

    init {
        viewModelScope.launch {
            val devices = deviceRepository.getDevices()
            this@LogsViewModel.devices.addAll(devices)
            device = devices.firstOrNull {
                it.id == (context
                    .getPreference(IntPreferencesKey.SELECTED_DEVICE_ID) ?: 1)
            }
        }
    }

    private fun List<Instant>.distinctByDate() = distinctBy {
        it.toLocalDateTime().date
    }

    private fun tryFetchLogs(type: LogsGroupType) {
        viewModelScope.launch {
            try {
                fetchingLogs = true
                when (type) {
                    LogsGroupType.SOIL_MOISTURE -> {
                        val logs = soilMoistureLogRepository
                            .getSoilMoistureLogs(
                                deviceId = device?.id ?: throw IllegalStateException("No device selected")
                            )
                            .sortedByDescending { it.timestamp }

                        logs.firstOrNull()?.let {
                            selectedDate = it.timestamp
                        }
                        soilMoistureLogs.clear()
                        availableDates.clear()
                        availableDates.addAll(
                            logs.map { it.timestamp }
                                .distinctByDate()
                        )
                        soilMoistureLogs.addAll(logs)
                    }
                    LogsGroupType.WATERING -> {
                        val logs = wateringRepository
                            .getWateringLogs(
                                deviceId = device?.id ?: throw IllegalStateException("No device selected")
                            )
                            .sortedByDescending { it.timestamp }

                        logs.firstOrNull()?.let {
                            selectedDate = it.timestamp
                        }
                        wateringLogs.clear()
                        availableDates.clear()
                        availableDates.addAll(
                            logs.map { it.timestamp }
                                .distinctByDate()
                        )
                        wateringLogs.addAll(logs)
                    }
                    else -> Unit
                }
            } finally {
                fetchingLogs = false
            }
        }
    }

    fun updateSelectedLogsGroup(type: LogsGroupType?) {
        selectedLogsGroup = type
        type?.let(::tryFetchLogs) ?: {
            selectedDate = null
        }()
    }

    fun updateSelectedDate(date: Instant) {
        selectedDate = date
    }

    fun onDeviceChange(device: Device) {
        this.device = device
        viewModelScope.launch {
            context.updatePreferences(
                key = IntPreferencesKey.SELECTED_DEVICE_ID,
                value = device.id
            )
            selectedLogsGroup?.let(::tryFetchLogs)
        }
    }
}