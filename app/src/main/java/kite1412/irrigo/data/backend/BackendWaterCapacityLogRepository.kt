package kite1412.irrigo.data.backend

import kite1412.irrigo.domain.WaterCapacityLogRepository
import kite1412.irrigo.model.WaterCapacityLog
import kotlinx.coroutines.flow.Flow

class BackendWaterCapacityLogRepository : WaterCapacityLogRepository {
    override fun getLatestWaterCapacityLogFlow(deviceId: Int): Flow<WaterCapacityLog> {
        TODO("Not yet implemented")
    }

    override fun getWaterCapacityLogs(deviceId: Int): List<WaterCapacityLog> {
        TODO("Not yet implemented")
    }
}