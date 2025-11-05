package kite1412.irrigo.domain

import kite1412.irrigo.model.WateringLog
import kotlinx.coroutines.flow.Flow

interface WateringLogRepository {
    fun getWateringLogsFlow(deviceId: Int): Flow<List<WateringLog>>
}