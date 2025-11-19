package kite1412.irrigo.data.backend.dto.response

import kite1412.irrigo.model.Device
import kite1412.irrigo.model.WateringLog
import kotlinx.serialization.Contextual
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlin.time.Instant

@Serializable
data class BackendWateringLogs(
    val id: Int,
    @SerialName("device_id")
    val deviceId: Int,
    @SerialName("duration_ms")
    val durationMs: Int,
    val manual: Boolean,
    @SerialName("created_at")
    @Contextual
    val createdAt: Instant
)

fun BackendWateringLogs.asModel() = WateringLog(
    id = id,
    device = Device(1, ""),
    timestamp = createdAt,
    durationMs = durationMs,
    waterVolumeLiters = 0.0,
    manual = manual
)
