package kite1412.irrigo.data.backend.util

enum class WebSocketMessageType(val value: String) {
    WATER_CAPACITY_LOG("water_capacity_log"),
    SOIL_MOISTURE_LOG("soil_moisture_log"),
    LIGHT_INTENSITY_LOG("light_intensity_log"),
    WATERING_LOG("watering_log")
}