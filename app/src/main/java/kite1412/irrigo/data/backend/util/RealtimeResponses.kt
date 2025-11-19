package kite1412.irrigo.data.backend.util

import kite1412.irrigo.model.Device
import kite1412.irrigo.model.LightIntensityLog
import kite1412.irrigo.model.LightIntensityStatus
import kite1412.irrigo.model.SoilMoistureLog
import kite1412.irrigo.model.WaterCapacityLog
import kite1412.irrigo.model.WaterContainer
import kite1412.irrigo.model.WateringLog
import kotlinx.serialization.json.JsonObject
import kotlinx.serialization.json.boolean
import kotlinx.serialization.json.double
import kotlinx.serialization.json.int
import kotlinx.serialization.json.jsonPrimitive
import kotlin.time.Instant

private fun JsonObject.getOrThrow(key: String) =
    get(key)?.jsonPrimitive ?: throw IllegalArgumentException("Missing key: $key")

fun SoilMoistureLog(jsonObject: JsonObject) = SoilMoistureLog(
    id = jsonObject.getOrThrow("id").int,
    device = Device(
        id = jsonObject.getOrThrow("device_id").int,
        name = ""
    ),
    moisturePercent = jsonObject.getOrThrow("moisture_percentage").double,
    timestamp = Instant.parse(jsonObject.getOrThrow("timestamp").content)
)

fun WateringLog(jsonObject: JsonObject) = WateringLog(
    id = jsonObject.getOrThrow("id").int,
    device = Device(
        id = jsonObject.getOrThrow("device_id").int,
        name = ""
    ),
    timestamp = Instant.parse(jsonObject.getOrThrow("timestamp").content),
    durationMs = jsonObject.getOrThrow("duration_ms").int,
    waterVolumeLiters = 0.0,
    manual = jsonObject.getOrThrow("manual").boolean
)

fun WaterCapacityLog(jsonObject: JsonObject) = WaterCapacityLog(
    id = jsonObject.getOrThrow("id").int,
    waterContainer = WaterContainer(
        device = Device(
            id = jsonObject.getOrThrow("device_id").int,
            name = ""
        ),
        heightCm = 0.0,
        capacityLitres = 0.0
    ),
    timestamp = Instant.parse(jsonObject.getOrThrow("timestamp").content),
    currentHeightCm = jsonObject.getOrThrow("current_height_cm").double,
    currentLitres = jsonObject.getOrThrow("current_litres").double
)

fun LightIntensityLog(jsonObject: JsonObject) = LightIntensityLog(
    device = Device(
        id = jsonObject.getOrThrow("device_id").int,
        name = ""
    ),
    lux = jsonObject.getOrThrow("lux").double,
    status = LightIntensityStatus.valueOf(jsonObject.getOrThrow("status").content.uppercase()),
    timestamp = Instant.parse(jsonObject.getOrThrow("timestamp").content)
)