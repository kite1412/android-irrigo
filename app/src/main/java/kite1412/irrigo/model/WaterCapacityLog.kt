package kite1412.irrigo.model

import kotlin.time.Instant

data class WaterCapacityLog(
    val id: Int,
    val waterContainer: WaterContainer,
    val timestamp: Instant,
    val currentHeightCm: Double,
    val currentLitres: Double? = null
)