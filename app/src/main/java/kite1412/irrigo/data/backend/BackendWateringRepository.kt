package kite1412.irrigo.data.backend

import kite1412.irrigo.domain.WateringRepository
import kite1412.irrigo.model.WateringConfig
import kite1412.irrigo.model.WateringLog
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class BackendWateringRepository @Inject constructor() : WateringRepository {
    override fun getLatestWateringLog(deviceId: Int): Flow<WateringLog> {
        TODO("Not yet implemented")
    }

    override fun getWateringLogs(deviceId: Int): List<WateringLog> {
        TODO("Not yet implemented")
    }

    override suspend fun performWatering(deviceId: Int): Boolean {
        TODO("Not yet implemented")
    }

    override suspend fun getConfig(): WateringConfig? {
        TODO("Not yet implemented")
    }

    override suspend fun updateConfig(config: WateringConfig): WateringConfig? {
        TODO("Not yet implemented")
    }
}