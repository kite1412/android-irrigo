package kite1412.irrigo.domain

import kite1412.irrigo.model.WateringConfig
import kite1412.irrigo.model.WateringLog
import kotlinx.coroutines.flow.Flow

interface WateringRepository {
    fun getLatestWateringLog(deviceId: Int): Flow<WateringLog>
    fun getWateringLogs(deviceId: Int): List<WateringLog>
    suspend fun getConfig(): WateringConfig?
}