package kite1412.irrigo.model

import kotlin.time.Instant

data class LightIntensityLog(
    val id: Int,
    val device: Device,
    val lux: Double,
    val status: LightIntensityStatus,
    val timestamp: Instant
)
