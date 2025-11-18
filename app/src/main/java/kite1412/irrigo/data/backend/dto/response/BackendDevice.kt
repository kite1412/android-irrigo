package kite1412.irrigo.data.backend.dto.response

import kite1412.irrigo.model.Device
import kotlinx.serialization.Serializable

@Serializable
data class BackendDevice(
    val id: Int,
    val name: String
)

fun BackendDevice.asModel() = Device(id, name)
