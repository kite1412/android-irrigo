package kite1412.irrigo.data.mock.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kite1412.irrigo.data.mock.MockWateringRepository
import kite1412.irrigo.domain.WateringRepository
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
interface MockModule {
//    @Binds
//    @Singleton
//    fun bindDeviceRepository(impl: MockDeviceRepository): DeviceRepository

//    @Binds
//    @Singleton
//    fun bindWaterCapacityRepository(impl: MockWaterCapacityRepository): WaterCapacityRepository

    @Binds
    @Singleton
    fun bindWateringRepository(impl: MockWateringRepository): WateringRepository

//    @Binds
//    @Singleton
//    fun bindSoilMoistureLogRepository(impl: MockSoilMoistureLogRepository): SoilMoistureLogRepository
}