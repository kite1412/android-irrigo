package kite1412.irrigo.data.mock

import kite1412.irrigo.domain.LightIntensityLogRepository
import kite1412.irrigo.model.LightIntensityLog
import kite1412.irrigo.model.LightIntensityStatus
import kite1412.irrigo.util.now
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.channelFlow
import kotlinx.coroutines.isActive
import javax.inject.Inject
import kotlin.random.Random
import kotlin.time.Duration.Companion.minutes
import kotlin.time.Duration.Companion.seconds

class MockLightIntensityRepository @Inject constructor() : LightIntensityLogRepository {
    override fun getLatestLightIntensityLog(deviceId: Int): Flow<LightIntensityLog> = channelFlow {
        var id = 0
        while (isActive) {
            val lux = Random.nextDouble(300.0, 350.0)
            send(
                LightIntensityLog(
                    id = --id,
                    device = MockData.devices.first(),
                    lux = lux,
                    status = getStatusFromLux(lux),
                    timestamp = now()
                )
            )
            delay(2.seconds)
        }
    }

    override suspend fun getLightIntensityLogs(deviceId: Int): List<LightIntensityLog> =
        List(20) { i ->
            val lux = Random.nextDouble(300.0, 350.0)
            LightIntensityLog(
                id = i + 1,
                device = MockData.devices.first(),
                lux = lux,
                status = getStatusFromLux(lux),
                timestamp = now() - (i * 30).minutes
            )
        }

    private fun getStatusFromLux(lux: Double): LightIntensityStatus {
        return when {
            lux < 200 -> LightIntensityStatus.LOW
            lux <= 800 -> LightIntensityStatus.NORMAL
            else -> LightIntensityStatus.HIGH
        }
    }
}
