package kite1412.irrigo.domain

import kite1412.irrigo.model.WateringConfig
import kite1412.irrigo.model.WateringLog
import kotlinx.coroutines.flow.Flow

interface WateringRepository {
    fun getWateringLogsFlow(deviceId: Int): Flow<List<WateringLog>>
    suspend fun getConfig(): WateringConfig
}