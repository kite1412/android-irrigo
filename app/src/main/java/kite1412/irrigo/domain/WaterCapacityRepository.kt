package kite1412.irrigo.domain

import kite1412.irrigo.model.WaterCapacityConfig
import kite1412.irrigo.model.WaterCapacityLog
import kotlinx.coroutines.flow.Flow

interface WaterCapacityRepository {
    fun getLatestWaterCapacityLogFlow(deviceId: Int): Flow<WaterCapacityLog>
    fun getWaterCapacityLogs(deviceId: Int): List<WaterCapacityLog>
    fun getConfig(): WaterCapacityConfig?
}