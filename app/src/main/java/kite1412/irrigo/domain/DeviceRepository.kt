package kite1412.irrigo.domain

import kite1412.irrigo.model.Device
import kite1412.irrigo.model.WaterContainer

interface DeviceRepository {
    suspend fun getDevices(): List<Device>
    suspend fun getDeviceById(id: Int): Device?
    suspend fun addDevice(device: Device): Device?
    suspend fun editDevice(device: Device): Device?
    suspend fun getWaterContainer(deviceId: Int): WaterContainer?
    suspend fun addWaterContainer(waterContainer: WaterContainer): WaterContainer?
    suspend fun editWaterContainer(waterContainer: WaterContainer): WaterContainer?
    suspend fun sendWateringSignal(deviceId: Int): Boolean
}