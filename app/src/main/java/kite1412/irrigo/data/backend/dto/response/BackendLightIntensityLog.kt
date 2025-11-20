package kite1412.irrigo.data.backend.dto.response

import kite1412.irrigo.model.Device
import kite1412.irrigo.model.LightIntensityLog
import kite1412.irrigo.model.LightIntensityStatus
import kotlinx.serialization.Contextual
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlin.time.Instant

@Serializable
data class BackendLightIntensityLog(
    val id: Int,
    @SerialName("device_id")
    val deviceId: Int,
    val lux: Double,
    @SerialName("created_at")
    @Contextual
    val createdAt: Instant
)

fun BackendLightIntensityLog.asModel() = LightIntensityLog(
    id = id,
    device = Device(
        id = deviceId,
        name = ""
    ),
    lux = lux,
    status = when (lux) {
        in 0.0..199.0 -> LightIntensityStatus.LOW
        in 200.0..800.0 -> LightIntensityStatus.NORMAL
        else -> LightIntensityStatus.HIGH
    },
    timestamp = createdAt
)
