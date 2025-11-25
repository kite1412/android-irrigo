package kite1412.irrigo.notification

import android.Manifest
import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import androidx.annotation.RequiresPermission
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import kite1412.irrigo.designsystem.util.IrrigoIcon
import kite1412.irrigo.notification.util.ReminderType

const val MAIN_ACTIVITY_NAME = "kite1412.irrigo.MainActivity"

@RequiresPermission(Manifest.permission.POST_NOTIFICATIONS)
fun Context.sendReminderNotification(
    reminderType: ReminderType,
    title: String,
    content: String
) {
    NotificationManagerCompat.from(this)
        .notify(
            reminderType.ordinal,
            createNotification(
                channelId = reminderType.channelId,
                channelName = reminderType.channelName,
                channelDesc = reminderType.channelDesc
            ) {
                setContentTitle(title)
                setContentText(content)
            }
        )
}

private fun Context.createNotification(
    channelId: String,
    channelName: String,
    channelDesc: String? = null,
    block: NotificationCompat.Builder.() -> Unit
): Notification {
    ensureNotificationChannelExists(
        channelId = channelId,
        channelName = channelName,
        channelDesc = channelDesc
    )

    return NotificationCompat.Builder(this, channelId)
        .setPriority(NotificationCompat.PRIORITY_HIGH)
        .apply {
            setSmallIcon(IrrigoIcon.logoLeaf)
            setAutoCancel(true)
            setContentIntent(notificationContentIntentNotifier())
        }
        .apply(block)
        .build()
}

private fun Context.ensureNotificationChannelExists(
    channelId: String,
    channelName: String,
    channelDesc: String? = null
) {
    val channel = NotificationChannel(
        channelId,
        channelName,
        NotificationManager.IMPORTANCE_HIGH
    ).apply {
        channelDesc?.let {
            description = it
        }
    }

    NotificationManagerCompat.from(this).createNotificationChannel(channel)
}

private fun Context.notificationContentIntentNotifier() = PendingIntent.getActivity(
    this,
    0,
    Intent().apply {
        action = Intent.ACTION_VIEW
        component = ComponentName(
            this@notificationContentIntentNotifier.packageName,
            MAIN_ACTIVITY_NAME
        )
    },
    PendingIntent.FLAG_IMMUTABLE or PendingIntent.FLAG_UPDATE_CURRENT
)