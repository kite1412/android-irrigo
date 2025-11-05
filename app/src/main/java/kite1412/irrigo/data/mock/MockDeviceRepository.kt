package kite1412.irrigo.data.mock

import kite1412.irrigo.domain.DeviceRepository
import kite1412.irrigo.model.Device
import javax.inject.Inject

class MockDeviceRepository @Inject constructor() : DeviceRepository {
    private val devices = List(10) { index ->
        Device(
            id = index + 1,
            name = "Device ${index + 1}"
        )
    }

    override suspend fun getDevices(): List<Device> = devices

    override suspend fun getDeviceById(id: Int): Device? = devices.firstOrNull()
}