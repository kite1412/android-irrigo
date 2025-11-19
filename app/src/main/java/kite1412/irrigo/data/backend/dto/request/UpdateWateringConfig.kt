package kite1412.irrigo.data.backend.dto.request

import kite1412.irrigo.model.WateringConfig
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class UpdateWateringConfig(
    @SerialName("min_soil_moisture_percent")
    val minSoilMoisturePercent: Double,
    @SerialName("duration_ms")
    val durationMs: Int,
    val automated: Boolean
)

fun WateringConfig.asRequestBody() = UpdateWateringConfig(
    minSoilMoisturePercent = minSoilMoisturePercent,
    durationMs = durationMs,
    automated = automated
)