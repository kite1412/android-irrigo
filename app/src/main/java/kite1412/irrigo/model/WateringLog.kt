package kite1412.irrigo.model

import kotlin.time.Instant

data class WateringLog(
    val id: Int,
    val device: Device,
    val timestamp: Instant,
    val durationMinutes: Int,
    val waterVolumeLiters: Double,
    val manual: Boolean = false
)