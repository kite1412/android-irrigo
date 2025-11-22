package kite1412.irrigo.feature.dashboard.util

import kite1412.irrigo.model.Device
import kite1412.irrigo.model.WaterContainer

data class DeviceEdit(
    val isNew: Boolean,
    val device: Device,
    val waterContainer: WaterContainer
)
