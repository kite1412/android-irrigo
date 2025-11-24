package kite1412.irrigo.data.backend.firebase

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class RegisterDeviceToken(
    @SerialName("android_id")
    val androidId: String,
    @SerialName("fcm_token")
    val fcmToken: String
)
