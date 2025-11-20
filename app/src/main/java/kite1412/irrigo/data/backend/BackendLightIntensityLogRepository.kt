package kite1412.irrigo.data.backend

import kite1412.irrigo.data.backend.dto.response.BackendLightIntensityLog
import kite1412.irrigo.data.backend.dto.response.asModel
import kite1412.irrigo.data.backend.util.ApiPaths.LIGHT_INTENSITY_LOGS
import kite1412.irrigo.data.backend.util.BackendResult
import kite1412.irrigo.data.backend.util.WebSocketMessageType
import kite1412.irrigo.domain.LightIntensityLogRepository
import kite1412.irrigo.model.LightIntensityLog
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import kite1412.irrigo.data.backend.util.LightIntensityLog as _LightIntensityLog

class BackendLightIntensityLogRepository @Inject constructor() :
    BackendClient(),
    LightIntensityLogRepository
{
    override fun getLatestLightIntensityLog(deviceId: Int): Flow<LightIntensityLog> =
        observeMessages(WebSocketMessageType.LIGHT_INTENSITY_LOG)
            .map(::_LightIntensityLog)

    override suspend fun getLightIntensityLogs(deviceId: Int): List<LightIntensityLog> {
        val res = get<List<BackendLightIntensityLog>>("$LIGHT_INTENSITY_LOGS/$deviceId")

        when (res) {
            is BackendResult.Success -> return res.data?.map(BackendLightIntensityLog::asModel)
                ?: emptyList()
            is BackendResult.Error -> throw res.throwable
        }
    }
}