package kite1412.irrigo.data.backend

import kite1412.irrigo.data.backend.dto.request.UpdateWateringConfig
import kite1412.irrigo.data.backend.dto.request.asRequestBody
import kite1412.irrigo.data.backend.dto.response.BackendWateringConfig
import kite1412.irrigo.data.backend.dto.response.BackendWateringLogs
import kite1412.irrigo.data.backend.dto.response.asModel
import kite1412.irrigo.data.backend.util.ApiPaths.WATERING_CONFIG
import kite1412.irrigo.data.backend.util.ApiPaths.WATERING_LOGS
import kite1412.irrigo.data.backend.util.BackendResult
import kite1412.irrigo.data.mock.MockWateringRepository
import kite1412.irrigo.domain.WateringRepository
import kite1412.irrigo.model.WateringConfig
import kite1412.irrigo.model.WateringLog
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class BackendWateringRepository @Inject constructor(
    private val mockRepository: MockWateringRepository
) : BackendClient(),
    WateringRepository
{

    override fun getLatestWateringLog(deviceId: Int): Flow<WateringLog> =
        mockRepository.getLatestWateringLog(deviceId)

    override suspend fun getWateringLogs(deviceId: Int): List<WateringLog> {
        val res = get<List<BackendWateringLogs>>("${WATERING_LOGS}/$deviceId")

        return when (res) {
            is BackendResult.Success -> res.data?.map(BackendWateringLogs::asModel)
                ?: emptyList()
            is BackendResult.Error -> throw res.throwable
        }
    }

    override suspend fun performWatering(deviceId: Int): Boolean {
        val res = post<Unit, Unit>("$WATERING_CONFIG/water-now")

        return when (res) {
            is BackendResult.Success -> true
            is BackendResult.Error -> false
        }
    }

    override suspend fun getConfig(): WateringConfig? {
        val res = get<BackendWateringConfig>(WATERING_CONFIG)

        return when (res) {
            is BackendResult.Success -> res.data?.asModel()
            is BackendResult.Error -> null
        }
    }

    override suspend fun updateConfig(config: WateringConfig): WateringConfig? {
        val res = patch<UpdateWateringConfig, Unit>(
            path = WATERING_CONFIG,
            body = config.asRequestBody()
        )

        return when (res) {
            is BackendResult.Success -> config
            is BackendResult.Error -> null
        }
    }
}