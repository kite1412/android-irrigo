package kite1412.irrigo.model

import kotlin.time.Instant

data class LightIntensityLog(
    val device: Device,
    val lux: Int,
    val status: LightIntensityStatus,
    val timestamp: Instant
)
