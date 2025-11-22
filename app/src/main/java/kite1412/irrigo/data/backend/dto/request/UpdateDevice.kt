package kite1412.irrigo.data.backend.dto.request

import kite1412.irrigo.model.Device
import kotlinx.serialization.Serializable

@Serializable
data class UpdateDevice(
    val name: String
) {
    companion object {
        fun fromModel(model: Device) = UpdateDevice(
            name = model.name
        )
    }
}