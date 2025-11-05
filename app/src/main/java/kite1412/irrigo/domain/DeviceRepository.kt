package kite1412.irrigo.domain

import kite1412.irrigo.model.Device

interface DeviceRepository {
    suspend fun getDevices(): List<Device>
    suspend fun getDeviceById(id: Int): Device?
}