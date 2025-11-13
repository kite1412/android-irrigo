package kite1412.irrigo.data.backend

import kite1412.irrigo.domain.WaterCapacityRepository
import kite1412.irrigo.model.WaterCapacityConfig
import kite1412.irrigo.model.WaterCapacityLog
import kotlinx.coroutines.flow.Flow

class BackendWaterCapacityRepository : WaterCapacityRepository {
    override fun getLatestWaterCapacityLogFlow(deviceId: Int): Flow<WaterCapacityLog> {
        TODO("Not yet implemented")
    }

    override fun getWaterCapacityLogs(deviceId: Int): List<WaterCapacityLog> {
        TODO("Not yet implemented")
    }

    override suspend fun getConfig(): WaterCapacityConfig? {
        TODO("Not yet implemented")
    }

    override suspend fun updateConfig(config: WaterCapacityConfig): WaterCapacityConfig? {
        TODO("Not yet implemented")
    }
}