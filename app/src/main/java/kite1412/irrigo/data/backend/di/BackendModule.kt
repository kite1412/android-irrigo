package kite1412.irrigo.data.backend.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kite1412.irrigo.data.backend.BackendDeviceRepository
import kite1412.irrigo.data.backend.BackendSoilMoistureLogRepository
import kite1412.irrigo.data.backend.BackendWaterCapacityRepository
import kite1412.irrigo.data.backend.BackendWateringRepository
import kite1412.irrigo.domain.DeviceRepository
import kite1412.irrigo.domain.SoilMoistureLogRepository
import kite1412.irrigo.domain.WaterCapacityRepository
import kite1412.irrigo.domain.WateringRepository
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
interface BackendModule {
    @Binds
    @Singleton
    fun bindDeviceRepository(
        impl: BackendDeviceRepository
    ): DeviceRepository

    @Binds
    @Singleton
    fun bindWaterCapacityRepository(
        impl: BackendWaterCapacityRepository
    ): WaterCapacityRepository

    @Binds
    @Singleton
    fun bindSoilMoistureLogRepository(
        impl: BackendSoilMoistureLogRepository
    ): SoilMoistureLogRepository

    @Binds
    @Singleton
    fun bindWateringRepository(
        impl: BackendWateringRepository
    ): WateringRepository
}