package kite1412.irrigo.data.backend

import kite1412.irrigo.domain.LightIntensityLogRepository
import kite1412.irrigo.model.LightIntensityLog
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emptyFlow
import javax.inject.Inject

class BackendLightIntensityLogRepository @Inject constructor() :
    BackendClient(),
    LightIntensityLogRepository
{
    override fun getLatestLightIntensityLog(deviceId: Int): Flow<LightIntensityLog> =
        emptyFlow()
}