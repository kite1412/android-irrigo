package kite1412.irrigo.data.mock

import kite1412.irrigo.model.Device
import kite1412.irrigo.model.WaterContainer

object MockData {
    val devices = List(10) { index ->
        Device(
            id = index + 1,
            name = "Device ${index + 1}"
        )
    }
    val waterContainer = WaterContainer(
        device = devices.first(),
        heightCm = 10.0,
        capacityLitres = 5.0
    )
}