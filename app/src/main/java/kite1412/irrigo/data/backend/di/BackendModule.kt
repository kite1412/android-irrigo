package kite1412.irrigo.data.backend.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kite1412.irrigo.data.backend.BackendDeviceRepository
import kite1412.irrigo.data.backend.BackendWaterCapacityRepository
import kite1412.irrigo.domain.DeviceRepository
import kite1412.irrigo.domain.WaterCapacityRepository
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
}