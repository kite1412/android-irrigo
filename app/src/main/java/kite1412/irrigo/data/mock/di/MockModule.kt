package kite1412.irrigo.data.mock.di

import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
interface MockModule {
//    @Binds
//    @Singleton
//    fun bindDeviceRepository(impl: MockDeviceRepository): DeviceRepository

//    @Binds
//    @Singleton
//    fun bindWaterCapacityRepository(impl: MockWaterCapacityRepository): WaterCapacityRepository

//    @Binds
//    @Singleton
//    fun bindWateringRepository(impl: MockWateringRepository): WateringRepository

//    @Binds
//    @Singleton
//    fun bindSoilMoistureLogRepository(impl: MockSoilMoistureLogRepository): SoilMoistureLogRepository
}