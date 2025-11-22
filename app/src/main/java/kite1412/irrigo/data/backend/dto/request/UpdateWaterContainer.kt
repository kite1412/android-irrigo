package kite1412.irrigo.data.backend.dto.request

import kite1412.irrigo.model.WaterContainer
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class UpdateWaterContainer(
    @SerialName("height_cm")
    val heightCm: Double,
    @SerialName("capacity_litres")
    val capacityLitres: Double
) {
    companion object {
        fun fromModel(model: WaterContainer) = UpdateWaterContainer(
            heightCm = model.heightCm,
            capacityLitres = model.capacityLitres
        )
    }
}