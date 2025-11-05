package kite1412.irrigo.domain

import kite1412.irrigo.model.SoilMoistureLog
import kotlinx.coroutines.flow.Flow

interface SoilMoistureLogRepository {
    fun getLatestSoilMoistureLog(deviceId: Int): Flow<SoilMoistureLog>
    fun getSoilMoistureLogsFlow(deviceId: Int): Flow<List<SoilMoistureLog>>
}