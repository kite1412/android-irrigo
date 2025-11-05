package kite1412.irrigo.data.mock.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kite1412.irrigo.data.mock.MockDeviceRepository
import kite1412.irrigo.domain.DeviceRepository
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
interface MockModule {
    @Binds
    @Singleton
    fun bindDeviceRepository(impl: MockDeviceRepository): DeviceRepository
}