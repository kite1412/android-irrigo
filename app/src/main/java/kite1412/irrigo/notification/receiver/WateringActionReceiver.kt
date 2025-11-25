package kite1412.irrigo.notification.receiver

import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import dagger.hilt.android.AndroidEntryPoint
import kite1412.irrigo.domain.DeviceRepository
import kite1412.irrigo.notification.cancelNotification
import kite1412.irrigo.notification.util.ReminderType
import kite1412.irrigo.util.IntPreferencesKey
import kite1412.irrigo.util.getPreference
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

const val WATERING_ACTION = "kite1412.irrigo.WATERING_ACTION"

@AndroidEntryPoint
class WateringActionReceiver : BroadcastReceiver() {

    @Inject
    lateinit var deviceRepository: DeviceRepository

    override fun onReceive(context: Context?, intent: Intent?) {
        if (context == null || intent == null) return
        CoroutineScope(Dispatchers.Default).launch {
            context.cancelNotification(ReminderType.WATERING.ordinal)

            val deviceId = context.getPreference(IntPreferencesKey.SELECTED_DEVICE_ID)

            deviceId?.let { deviceId ->
                deviceRepository.sendWateringSignal(deviceId)
            }
        }
    }
}

fun Context.wateringActionPendingIntent() = PendingIntent.getBroadcast(
    this,
    1,
    Intent(this, WateringActionReceiver::class.java).apply {
        action = WATERING_ACTION
    },
    PendingIntent.FLAG_IMMUTABLE or PendingIntent.FLAG_UPDATE_CURRENT
)