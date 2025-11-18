package kite1412.irrigo.data.backend.dto.response

import kite1412.irrigo.model.WaterCapacityConfig
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class BackendWaterCapacityConfig(
    val id: Int,
    @SerialName("min_water_capacity_percent")
    val minWaterCapacityPercent: Double
)

fun BackendWaterCapacityConfig.asModel() =
    WaterCapacityConfig(minWaterCapacityPercent)