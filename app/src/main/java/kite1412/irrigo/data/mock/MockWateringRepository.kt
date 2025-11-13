package kite1412.irrigo.data.mock

import kite1412.irrigo.domain.WateringRepository
import kite1412.irrigo.model.WateringConfig
import kite1412.irrigo.model.WateringLog
import kite1412.irrigo.util.now
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.channelFlow
import javax.inject.Inject
import kotlin.time.Duration

class MockWateringRepository @Inject constructor() : WateringRepository {
    override fun getLatestWateringLog(deviceId: Int): Flow<WateringLog> =
        channelFlow {
            var id = 0

            while (true) {
                trySend(
                    WateringLog(
                        id = --id,
                        device = MockData.devices.first(),
                        timestamp = now(),
                        waterVolumeLiters = 1.0,
                        durationMs = 2000,
                        manual = id % 2 == 0
                    )
                )
                delay(2000L)
            }
        }

    override fun getWateringLogs(deviceId: Int): List<WateringLog> =
        List(100) {
            WateringLog(
                id = it + 1,
                device = MockData.devices.first(),
                timestamp = now() - Duration.parse("${it}h"),
                durationMs = 2000,
                waterVolumeLiters = 1.0,
                manual = it % 2 == 0
            )
        }

    override suspend fun getConfig(): WateringConfig? = WateringConfig(
        minSoilMoisturePercent = 40f,
        durationMs = 2000,
        automated = true
    )
}