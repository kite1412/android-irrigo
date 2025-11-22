package kite1412.irrigo.data.backend.dto.request

import kite1412.irrigo.model.WaterContainer
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class CreateWaterContainer(
    @SerialName("device_id")
    val deviceId: Int,
    @SerialName("height_cm")
    val heightCm: Double,
    @SerialName("capacity_litres")
    val capacityLitres: Double
) {
    companion object {
        fun fromModel(model: WaterContainer) = CreateWaterContainer(
            deviceId = model.device.id,
            heightCm = model.heightCm,
            capacityLitres = model.capacityLitres
        )
    }
}
