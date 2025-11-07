package kite1412.irrigo.domain

import kite1412.irrigo.model.Device
import kite1412.irrigo.model.WaterContainer

interface DeviceRepository {
    suspend fun getDevices(): List<Device>
    suspend fun getDeviceById(id: Int): Device?
    suspend fun getWaterContainer(deviceId: Int): WaterContainer?
}