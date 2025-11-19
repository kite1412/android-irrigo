package kite1412.irrigo.data.backend.dto.response

import kite1412.irrigo.model.WateringConfig
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class BackendWateringConfig(
    val id: Int,
    @SerialName("min_soil_moisture_percent")
    val minSoilMoisturePercent: Double,
    @SerialName("duration_ms")
    val durationMs: Int,
    val automated: Boolean,
)

fun BackendWateringConfig.asModel() = WateringConfig(
    minSoilMoisturePercent = minSoilMoisturePercent,
    durationMs = durationMs,
    automated = automated
)