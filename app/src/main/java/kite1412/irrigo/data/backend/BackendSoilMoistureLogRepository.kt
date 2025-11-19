package kite1412.irrigo.data.backend

import kite1412.irrigo.data.backend.dto.response.BackendSoilMoistureLog
import kite1412.irrigo.data.backend.dto.response.asModel
import kite1412.irrigo.data.backend.util.ApiPaths.SOIL_MOISTURE_LOGS
import kite1412.irrigo.data.backend.util.BackendResult
import kite1412.irrigo.data.backend.util.WebSocketMessageType
import kite1412.irrigo.domain.SoilMoistureLogRepository
import kite1412.irrigo.model.SoilMoistureLog
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.mapLatest
import javax.inject.Inject
import kite1412.irrigo.data.backend.util.SoilMoistureLog as _SoilMoistureLog

class BackendSoilMoistureLogRepository @Inject constructor() :
    BackendClient(),
    SoilMoistureLogRepository
{
    @OptIn(ExperimentalCoroutinesApi::class)
    override fun getLatestSoilMoistureLog(deviceId: Int): Flow<SoilMoistureLog> =
        observeMessages(WebSocketMessageType.SOIL_MOISTURE_LOG)
            .mapLatest(::_SoilMoistureLog)

    override suspend fun getSoilMoistureLogs(deviceId: Int): List<SoilMoistureLog> {
        val res = get<List<BackendSoilMoistureLog>>("${SOIL_MOISTURE_LOGS}/$deviceId")

        return when (res) {
            is BackendResult.Success -> res.data?.map(BackendSoilMoistureLog::asModel)
                ?: emptyList()
            is BackendResult.Error -> throw res.throwable
        }
    }
}