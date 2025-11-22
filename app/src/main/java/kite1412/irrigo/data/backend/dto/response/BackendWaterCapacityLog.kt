package kite1412.irrigo.data.backend.dto.response

import kite1412.irrigo.model.Device
import kite1412.irrigo.model.WaterCapacityLog
import kite1412.irrigo.model.WaterContainer
import kotlinx.serialization.Contextual
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlin.time.Instant

@Serializable
data class BackendWaterCapacityLog(
    val id: Int,
    @SerialName("container_id")
    val containerId: Int,
    @SerialName("created_at")
    @Contextual
    val createdAt: Instant,
    @SerialName("current_height_cm")
    val currentHeightCm: Double,
    @SerialName("current_litres")
    val currentLitres: Double
)

fun BackendWaterCapacityLog.asModel() = WaterCapacityLog(
    id = id,
    waterContainer = WaterContainer(
        device = Device(1, ""),
        heightCm = 0.0,
        capacityLitres = 0.0
    ),
    timestamp = createdAt,
    currentHeightCm = currentHeightCm,
    currentLitres = currentLitres
)