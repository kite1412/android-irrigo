package kite1412.irrigo

import android.Manifest
import android.content.pm.PackageManager
import android.util.Log
import androidx.core.app.ActivityCompat
import com.google.firebase.messaging.RemoteMessage
import dagger.hilt.android.AndroidEntryPoint
import kite1412.irrigo.data.backend.firebase.DeviceTokenRegister
import kite1412.irrigo.notification.sendReminderNotification
import kite1412.irrigo.notification.util.ReminderType
import kite1412.irrigo.util.BooleanPreferencesKey
import kite1412.irrigo.util.getPreference
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject
import com.google.firebase.messaging.FirebaseMessagingService as _FirebaseMessagingService

@AndroidEntryPoint
class IrrigoFirebaseMessagingService : _FirebaseMessagingService() {

    private val logTag = "IrrigoFirebaseMessagingService"

    @Inject
    lateinit var deviceTokenRegister: DeviceTokenRegister

    override fun onNewToken(token: String) {
        super.onNewToken(token)
        CoroutineScope(Dispatchers.IO).launch {
            deviceTokenRegister(token)
        }
    }

    override fun onMessageReceived(message: RemoteMessage) {
        super.onMessageReceived(message)
        Log.d(logTag, "data: ${message.data}")

        CoroutineScope(Dispatchers.IO).launch {
            val type = message.data["type"]
            val title = message.data["title"]
            val content = message.data["body"]

            if (
                type != null &&
                title != null &&
                content != null
            ) {
                val push = when (type) {
                    "water_capacity_low" -> getPreference(
                        key = BooleanPreferencesKey.WATERING_REMINDER_ENABLED,
                        defaultValue = true
                    ) ?: true
                    "soil_moisture_below_min" -> getPreference(
                        key = BooleanPreferencesKey.WATER_CAPACITY_REMINDER_ENABLED,
                        defaultValue = true
                    ) ?: true
                    else -> true
                }

                if (
                    push && ActivityCompat.checkSelfPermission(
                        this@IrrigoFirebaseMessagingService, Manifest.permission.POST_NOTIFICATIONS
                    ) == PackageManager.PERMISSION_GRANTED
                ) sendReminderNotification(
                    reminderType = when (type) {
                        "water_capacity_low" -> ReminderType.WATER_CAPACITY
                        "soil_moisture_below_min" -> ReminderType.WATERING
                        else -> ReminderType.DUMMY
                    },
                    title = title,
                    content = content
                )
            }
        }
    }
}