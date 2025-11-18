package kite1412.irrigo.data.backend

import kite1412.irrigo.domain.SoilMoistureLogRepository
import kite1412.irrigo.model.SoilMoistureLog
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class BackendSoilMoistureLogRepository @Inject constructor() : SoilMoistureLogRepository {
    override fun getLatestSoilMoistureLog(deviceId: Int): Flow<SoilMoistureLog> {
        TODO("Not yet implemented")
    }

    override suspend fun getSoilMoistureLogs(deviceId: Int): List<SoilMoistureLog> {
        TODO("Not yet implemented")
    }
}