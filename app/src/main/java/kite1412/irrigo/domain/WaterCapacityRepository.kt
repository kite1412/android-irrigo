package kite1412.irrigo.domain

import kite1412.irrigo.model.WaterCapacityLog
import kotlinx.coroutines.flow.Flow

interface WaterCapacityRepository {
    fun getLatestWaterCapacity(deviceId: Int): Flow<WaterCapacityLog>
}