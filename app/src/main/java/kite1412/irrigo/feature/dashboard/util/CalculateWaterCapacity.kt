package kite1412.irrigo.feature.dashboard.util

fun getWaterLevelPercentString(
    heightCm: Double,
    currentHeightCm: Double
): String = "${((currentHeightCm / heightCm).coerceIn(0.0, 1.0) * 100).toInt()}%"