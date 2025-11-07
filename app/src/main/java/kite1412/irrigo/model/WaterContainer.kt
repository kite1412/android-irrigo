package kite1412.irrigo.model

data class WaterContainer(
    val device: Device,
    val heightCm: Double,
    val capacityLitres: Double? = null
)
