package kite1412.irrigo.data.mock

import kite1412.irrigo.domain.WateringRepository
import kite1412.irrigo.model.WateringConfig
import kite1412.irrigo.model.WateringLog
import kite1412.irrigo.util.now
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import kotlin.time.Duration

class MockWateringLogRepository @Inject constructor() : WateringRepository {
    override fun getLatestWateringLog(): Flow<WateringLog> {
        TODO("Not yet implemented")
    }

    override fun getWateringLogs(deviceId: Int): List<WateringLog> =
        List(100) {
            WateringLog(
                id = it + 1,
                device = MockData.devices.first(),
                timestamp = now() - Duration.parse("${it}h"),
                durationMs = 2000,
                waterVolumeLiters = 1.0
            )
        }

    override suspend fun getConfig(): WateringConfig = WateringConfig(
        minSoilMoisturePercent = 40f,
        durationMs = 2000,
        automated = true
    )
}