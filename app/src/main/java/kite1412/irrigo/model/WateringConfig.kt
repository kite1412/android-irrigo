package kite1412.irrigo.model

data class WateringConfig(
    val minSoilMoisturePercent: Float, // 0 - 100%
    val durationMs: Float,
    val automated: Boolean
)
