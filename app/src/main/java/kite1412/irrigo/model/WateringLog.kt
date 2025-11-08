package kite1412.irrigo.model

import kotlin.time.Instant

data class WateringLog(
    val id: Int,
    val device: Device,
    val timestamp: Instant,
    val durationSeconds: Int,
    val waterVolumeLiters: Double,
    val manual: Boolean = false
)