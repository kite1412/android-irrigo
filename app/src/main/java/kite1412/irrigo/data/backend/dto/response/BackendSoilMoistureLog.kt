package kite1412.irrigo.data.backend.dto.response

import kite1412.irrigo.model.SoilMoistureLog
import kotlinx.serialization.Contextual
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlin.time.Instant

@Serializable
data class BackendSoilMoistureLog(
    val id: Int,
    @SerialName("device_id")
    val deviceId: Int,
    @SerialName("moist_value")
    val moistValue: Double,
    @SerialName("created_at")
    @Contextual
    val createdAt: Instant,
    @SerialName("devices")
    val device: BackendDevice,
    @SerialName("moisture_percentage")
    val moisturePercentage: Double
)

fun BackendSoilMoistureLog.asModel() = SoilMoistureLog(
    id = id,
    device = device.asModel(),
    moisturePercent = moisturePercentage,
    timestamp = createdAt
)