package kite1412.irrigo.domain

import kite1412.irrigo.model.LightIntensityLog
import kotlinx.coroutines.flow.Flow

interface LightIntensityLogRepository {
    fun getLatestLightIntensityLog(deviceId: Int): Flow<LightIntensityLog>
    suspend fun getLightIntensityLogs(deviceId: Int): List<LightIntensityLog>
}