package kite1412.irrigo.domain

import kite1412.irrigo.model.WaterCapacityLog
import kotlinx.coroutines.flow.Flow

interface WaterCapacityLogRepository {
    fun getLatestWaterCapacityLogFlow(deviceId: Int): Flow<WaterCapacityLog>
}