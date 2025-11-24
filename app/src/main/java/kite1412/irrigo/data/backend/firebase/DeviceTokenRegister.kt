package kite1412.irrigo.data.backend.firebase

import android.annotation.SuppressLint
import android.content.Context
import android.provider.Settings
import dagger.hilt.android.qualifiers.ApplicationContext
import kite1412.irrigo.data.backend.BackendClient
import kite1412.irrigo.data.backend.util.BackendResult
import javax.inject.Inject

class DeviceTokenRegister @Inject constructor(
    @param:ApplicationContext val context: Context
) : BackendClient() {
    private val registerPath = "/register-device-token"

    @SuppressLint("HardwareIds")
    suspend operator fun invoke(token: String): DeviceToken? {
        val res = post<RegisterDeviceToken, DeviceToken>(
            path = registerPath,
            body = RegisterDeviceToken(
                androidId = Settings.Secure.getString(
                    context.contentResolver,
                    Settings.Secure.ANDROID_ID
                ),
                fcmToken = token
            )
        )

        when (res) {
            is BackendResult.Success -> return res.data
            is BackendResult.Error -> throw res.throwable
        }
    }
}