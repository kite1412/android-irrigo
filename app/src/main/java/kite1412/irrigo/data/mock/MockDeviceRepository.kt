package kite1412.irrigo.data.mock

import kite1412.irrigo.domain.DeviceRepository
import kite1412.irrigo.model.Device
import kite1412.irrigo.model.WaterContainer
import javax.inject.Inject

class MockDeviceRepository @Inject constructor() : DeviceRepository {
    override suspend fun getDevices(): List<Device> = MockData.devices

    override suspend fun getDeviceById(id: Int): Device? = MockData.devices.firstOrNull()
    override suspend fun getWaterContainer(deviceId: Int): WaterContainer? = MockData.waterContainer
}