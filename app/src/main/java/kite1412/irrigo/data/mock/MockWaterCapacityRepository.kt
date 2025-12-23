package kite1412.irrigo.data.mock

import kite1412.irrigo.domain.WaterCapacityRepository
import kite1412.irrigo.model.WaterCapacityConfig
import kite1412.irrigo.model.WaterCapacityLog
import kite1412.irrigo.util.now
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.channelFlow
import javax.inject.Inject
import kotlin.time.Duration

class MockWaterCapacityRepository @Inject constructor() : WaterCapacityRepository {
    override fun getLatestWaterCapacityLogFlow(deviceId: Int): Flow<WaterCapacityLog> = channelFlow {
        val waterContainer = MockData.waterContainer
        var id = 0

        while (true) {
//            val random = Random.nextDouble(
//                from = 0.7,
//                until = 1.0
//            )
            val fixed = 0.76f
            trySend(
                WaterCapacityLog(
                    id = --id,
                    waterContainer = waterContainer,
                    timestamp = now(),
                    currentHeightCm = waterContainer.heightCm * fixed,
                    currentLitres = waterContainer.capacityLitres * fixed
                )
            )
            delay(2000L)
        }
    }

    override suspend fun getWaterCapacityLogs(deviceId: Int): List<WaterCapacityLog> {
        val container = MockData.waterContainer

        return List(100) {
            WaterCapacityLog(
                id = it + 1,
                waterContainer = container,
                timestamp = now() - Duration.parse("${it}h"),
                currentHeightCm = container.heightCm * 0.76
            )
        }
    }

    override suspend fun getConfig(): WaterCapacityConfig =
        WaterCapacityConfig(
            minWaterCapacityPercent = 40.0
        )

    override suspend fun updateConfig(
        config: WaterCapacityConfig
    ): WaterCapacityConfig = config
}