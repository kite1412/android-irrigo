package kite1412.irrigo.data.mock

import kite1412.irrigo.domain.SoilMoistureLogRepository
import kite1412.irrigo.model.SoilMoistureLog
import kite1412.irrigo.util.now
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.channelFlow
import kotlinx.coroutines.isActive
import javax.inject.Inject
import kotlin.random.Random
import kotlin.time.Duration

class MockSoilMoistureLogRepository @Inject constructor() : SoilMoistureLogRepository {
    override fun getLatestSoilMoistureLog(deviceId: Int): Flow<SoilMoistureLog> =
        channelFlow {
            var id = 0

            while (isActive) {
                val latestLog = SoilMoistureLog(
                    id = --id,
                    device = MockData.devices.first(),
                    moisturePercent = (30 until 100).random() + Random.nextDouble(0.0, 1.0),
                    timestamp = now()
                )

                trySend(latestLog)
                delay(2000L)
            }
        }

    override suspend fun getSoilMoistureLogs(deviceId: Int): List<SoilMoistureLog> =
        List(50) { index ->
            SoilMoistureLog(
                id = index + 1,
                device = MockData.devices.first(),
                moisturePercent = (50..100).random() + (0..99).random() / 100.0,
                timestamp = now() - Duration.parse("${index}h")
            )
        }
}