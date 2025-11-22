package kite1412.irrigo.data.backend

import kite1412.irrigo.data.backend.dto.request.CreateWaterContainer
import kite1412.irrigo.data.backend.dto.request.UpdateDevice
import kite1412.irrigo.data.backend.dto.request.UpdateWaterContainer
import kite1412.irrigo.data.backend.dto.response.BackendDevice
import kite1412.irrigo.data.backend.dto.response.BackendWaterContainer
import kite1412.irrigo.data.backend.dto.response.asModel
import kite1412.irrigo.data.backend.util.ApiPaths.DEVICES
import kite1412.irrigo.data.backend.util.ApiPaths.WATERING_CONFIG
import kite1412.irrigo.data.backend.util.ApiPaths.WATER_CONTAINERS
import kite1412.irrigo.data.backend.util.BackendResult
import kite1412.irrigo.domain.DeviceRepository
import kite1412.irrigo.model.Device
import kite1412.irrigo.model.WaterContainer
import javax.inject.Inject

class BackendDeviceRepository @Inject constructor() :
    BackendClient(),
    DeviceRepository
{
    override suspend fun getDevices(): List<Device> {
        val res = get<List<BackendDevice>>(DEVICES)

        return when (res) {
            is BackendResult.Success -> res.data?.map(BackendDevice::asModel)
                ?: emptyList()
            is BackendResult.Error -> throw res.throwable
        }
    }

    override suspend fun getDeviceById(id: Int): Device? =
        getDevices().firstOrNull { it.id == id }

    override suspend fun addDevice(device: Device): Device? {
        val res = post<UpdateDevice, BackendDevice>(
            path = DEVICES,
            body = UpdateDevice.fromModel(device)
        )

        return when (res) {
            is BackendResult.Success -> res.data?.asModel()
            is BackendResult.Error -> throw res.throwable
        }
    }

    override suspend fun editDevice(
        device: Device
    ): Device? {
        val res = patch<UpdateDevice, BackendDevice>(
            path = "$DEVICES/${device.id}",
            body = UpdateDevice.fromModel(device)
        )

        return when (res) {
            is BackendResult.Success -> res.data?.asModel()
            is BackendResult.Error -> throw res.throwable
        }
    }

    override suspend fun getWaterContainer(deviceId: Int): WaterContainer? {
        val res = get<List<BackendWaterContainer>>("$WATER_CONTAINERS/$deviceId")

        return when (res) {
            is BackendResult.Success -> res.data
                ?.firstOrNull { it.deviceId == deviceId }
                ?.asModel()
            is BackendResult.Error -> throw res.throwable
        }
    }

    override suspend fun addWaterContainer(waterContainer: WaterContainer): WaterContainer? {
        val res = post<CreateWaterContainer, BackendWaterContainer>(
            path = "$WATER_CONTAINERS/${waterContainer.device.id}",
            body = CreateWaterContainer.fromModel(waterContainer)
        )

        return when (res) {
            is BackendResult.Success -> res.data?.asModel()
            is BackendResult.Error -> throw res.throwable
        }
    }

    override suspend fun editWaterContainer(waterContainer: WaterContainer): WaterContainer? {
        val res = patch<UpdateWaterContainer, BackendWaterContainer>(
            path = "$WATER_CONTAINERS/${waterContainer.device.id}",
            body = UpdateWaterContainer.fromModel(waterContainer)
        )

        return when (res) {
            is BackendResult.Success -> res.data?.asModel()
            is BackendResult.Error -> throw res.throwable
        }
    }

    override suspend fun sendWateringSignal(deviceId: Int): Boolean {
        val res = post<Unit, Unit>("$WATERING_CONFIG/water-now")

        return when (res) {
            is BackendResult.Success -> true
            is BackendResult.Error -> throw res.throwable
        }
    }
}