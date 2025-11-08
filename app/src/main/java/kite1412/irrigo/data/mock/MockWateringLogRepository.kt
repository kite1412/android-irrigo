package kite1412.irrigo.data.mock

import kite1412.irrigo.domain.WateringLogRepository
import kite1412.irrigo.model.WateringLog
import kite1412.irrigo.util.now
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.channelFlow
import javax.inject.Inject
import kotlin.time.Duration.Companion.days

class MockWateringLogRepository @Inject constructor() : WateringLogRepository {
    override fun getWateringLogsFlow(deviceId: Int): Flow<List<WateringLog>> =
        channelFlow {
            var id = 0
            var list = listOf<WateringLog>()

            while (true) {
                delay(1000)
                list = mutableListOf<WateringLog>().apply {
                    add(
                        WateringLog(
                            id = id,
                            device = MockData.devices.first(),
                            timestamp = now() + id.days,
                            durationSeconds = 2,
                            waterVolumeLiters = 0.5,
                            manual = false
                        )
                    )
                    id++
                    addAll(list)
                }

                trySend(list)
            }
        }
}