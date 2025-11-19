package kite1412.irrigo.domain

import kite1412.irrigo.model.WateringConfig
import kite1412.irrigo.model.WateringLog
import kotlinx.coroutines.flow.Flow

interface WateringRepository {
    fun getLatestWateringLog(deviceId: Int): Flow<WateringLog>
    suspend fun getWateringLogs(deviceId: Int): List<WateringLog>
    suspend fun performWatering(deviceId: Int): Boolean
    suspend fun getConfig(): WateringConfig?
    suspend fun updateConfig(config: WateringConfig): WateringConfig?
}