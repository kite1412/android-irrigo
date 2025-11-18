package kite1412.irrigo.data.backend.dto.request

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class UpdateWaterCapacityConfig(
    @SerialName("min_water_capacity_percent")
    val minWaterCapacityPercent: Double
)
