package kite1412.irrigo.data.backend

import kite1412.irrigo.data.backend.dto.request.UpdateWaterCapacityConfig
import kite1412.irrigo.data.backend.dto.response.BackendWaterCapacityConfig
import kite1412.irrigo.data.backend.dto.response.BackendWaterCapacityLog
import kite1412.irrigo.data.backend.dto.response.asModel
import kite1412.irrigo.data.backend.util.ApiPaths.WATER_CAPACITY_CONFIG
import kite1412.irrigo.data.backend.util.ApiPaths.WATER_CAPACITY_LOGS
import kite1412.irrigo.data.backend.util.BackendResult
import kite1412.irrigo.data.backend.util.WebSocketMessageType
import kite1412.irrigo.domain.WaterCapacityRepository
import kite1412.irrigo.model.WaterCapacityConfig
import kite1412.irrigo.model.WaterCapacityLog
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import kite1412.irrigo.data.backend.util.WaterCapacityLog as _WaterCapacityLog

class BackendWaterCapacityRepository @Inject constructor() :
    BackendClient(),
    WaterCapacityRepository
{
    override fun getLatestWaterCapacityLogFlow(deviceId: Int): Flow<WaterCapacityLog> =
        observeMessages(WebSocketMessageType.WATER_CAPACITY_LOG)
            .map(::_WaterCapacityLog)

    override suspend fun getWaterCapacityLogs(deviceId: Int): List<WaterCapacityLog> {
        val res = get<List<BackendWaterCapacityLog>>("${WATER_CAPACITY_LOGS}/$deviceId")

        return when (res) {
            is BackendResult.Success -> res.data?.map(BackendWaterCapacityLog::asModel)
                ?: emptyList()
            is BackendResult.Error -> throw res.throwable
        }
    }

    override suspend fun getConfig(): WaterCapacityConfig? {
        val res = get<BackendWaterCapacityConfig>(WATER_CAPACITY_CONFIG)

        return when (res) {
            is BackendResult.Success -> res.data?.asModel()
            is BackendResult.Error -> null
        }
    }

    override suspend fun updateConfig(config: WaterCapacityConfig): WaterCapacityConfig? {
        val res = patch<UpdateWaterCapacityConfig, Unit>(
            path = WATER_CAPACITY_CONFIG,
            body = UpdateWaterCapacityConfig(config.minWaterCapacityPercent)
        )

        return when (res) {
            is BackendResult.Success -> config
            is BackendResult.Error -> null
        }
    }
}