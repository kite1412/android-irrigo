package kite1412.irrigo.model

import kotlin.time.Instant

data class WaterCapacityLog(
    val id: Int,
    val device: Device,
    val timestamp: Instant,
    val currentLiters: Float
)