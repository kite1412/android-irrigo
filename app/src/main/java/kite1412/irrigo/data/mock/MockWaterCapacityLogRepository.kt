package kite1412.irrigo.data.mock

import kite1412.irrigo.domain.WaterCapacityLogRepository
import kite1412.irrigo.model.WaterCapacityLog
import kite1412.irrigo.util.now
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.channelFlow
import javax.inject.Inject
import kotlin.random.Random

class MockWaterCapacityLogRepository @Inject constructor() : WaterCapacityLogRepository {
    override fun getLatestWaterCapacityLogFlow(deviceId: Int): Flow<WaterCapacityLog> = channelFlow {
        val waterContainer = MockData.waterContainer

        while (true) {
            delay(500L)

            val random = Random.nextDouble(
                from = 0.7,
                until = 1.0
            )
            trySend(
                WaterCapacityLog(
                    id = 1,
                    device = MockData.devices.first(),
                    timestamp = now(),
                    currentHeightCm = waterContainer.heightCm * random,
                    currentLitres = waterContainer.capacityLitres?.times(random)
                )
            )
        }
    }
}