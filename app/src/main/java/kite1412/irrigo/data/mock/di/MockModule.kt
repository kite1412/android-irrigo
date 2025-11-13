package kite1412.irrigo.data.mock.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kite1412.irrigo.data.mock.MockDeviceRepository
import kite1412.irrigo.data.mock.MockSoilMoistureLogRepository
import kite1412.irrigo.data.mock.MockWaterCapacityRepository
import kite1412.irrigo.data.mock.MockWateringRepository
import kite1412.irrigo.domain.DeviceRepository
import kite1412.irrigo.domain.SoilMoistureLogRepository
import kite1412.irrigo.domain.WaterCapacityRepository
import kite1412.irrigo.domain.WateringRepository
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
interface MockModule {
    @Binds
    @Singleton
    fun bindDeviceRepository(impl: MockDeviceRepository): DeviceRepository

    @Binds
    @Singleton
    fun bindWaterCapacityLogRepository(impl: MockWaterCapacityRepository): WaterCapacityRepository

    @Binds
    @Singleton
    fun bindWateringRepository(impl: MockWateringRepository): WateringRepository

    @Binds
    @Singleton
    fun bindSoilMoistureLogRepository(impl: MockSoilMoistureLogRepository): SoilMoistureLogRepository
}