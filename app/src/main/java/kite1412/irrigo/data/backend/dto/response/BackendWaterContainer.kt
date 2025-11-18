package kite1412.irrigo.data.backend.dto.response

import kite1412.irrigo.model.Device
import kite1412.irrigo.model.WaterContainer
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonIgnoreUnknownKeys

@OptIn(ExperimentalSerializationApi::class)
@Serializable
@JsonIgnoreUnknownKeys
data class BackendWaterContainer(
    val id: Int,
    @SerialName("device_id")
    val deviceId: Int,
    @SerialName("height_cm")
    val heightCm: Double,
    @SerialName("capacity_litres")
    val capacityLitres: Double,
    // TODO delete
    @SerialName("devices")
    val device: BackendDevice? = null
)

fun BackendWaterContainer.asModel() = WaterContainer(
    device = Device(deviceId, ""),
    heightCm = heightCm,
    capacityLitres = capacityLitres
)
