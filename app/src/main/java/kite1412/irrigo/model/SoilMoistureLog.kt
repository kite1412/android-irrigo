package kite1412.irrigo.model

import kotlin.time.Instant

data class SoilMoistureLog(
    val id: Int,
    val device: Device,
    val moisturePercent: Double, // 0-100 %
    val timestamp: Instant
)