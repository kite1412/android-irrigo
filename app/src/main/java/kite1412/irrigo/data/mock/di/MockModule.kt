package kite1412.irrigo.data.mock.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kite1412.irrigo.data.mock.MockDeviceRepository
import kite1412.irrigo.data.mock.MockWaterCapacityLogRepository
import kite1412.irrigo.data.mock.MockWateringLogRepository
import kite1412.irrigo.domain.DeviceRepository
import kite1412.irrigo.domain.WaterCapacityLogRepository
import kite1412.irrigo.domain.WateringLogRepository
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
interface MockModule {
    @Binds
    @Singleton
    fun bindDeviceRepository(impl: MockDeviceRepository): DeviceRepository

    @Binds
    @Singleton
    fun bindWaterCapacityLogRepository(impl: MockWaterCapacityLogRepository): WaterCapacityLogRepository

    @Binds
    @Singleton
    fun bindWateringLogRepository(impl: MockWateringLogRepository): WateringLogRepository
}