package kite1412.irrigo.domain

import kite1412.irrigo.model.WaterCapacityLog
import kotlinx.coroutines.flow.Flow

interface WaterCapacityLogRepository {
    fun getLatestLogFlow(deviceId: Int): Flow<WaterCapacityLog>
}