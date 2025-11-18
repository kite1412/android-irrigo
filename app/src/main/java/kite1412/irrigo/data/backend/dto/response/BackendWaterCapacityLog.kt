package kite1412.irrigo.data.backend.dto.response

import kite1412.irrigo.model.WaterCapacityLog
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
    val currentLitres: Double,
    //TODO delete
    @SerialName("water_containers")
    val waterContainer: BackendWaterContainer
)

fun BackendWaterCapacityLog.asModel() = WaterCapacityLog(
    id = id,
    waterContainer = waterContainer.asModel(),
    timestamp = createdAt,
    currentHeightCm = currentHeightCm,
    currentLitres = currentLitres
)