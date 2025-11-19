package kite1412.irrigo.model

data class WateringConfig(
    val minSoilMoisturePercent: Double, // 0 - 100%
    val durationMs: Int,
    val automated: Boolean
)