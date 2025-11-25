package kite1412.irrigo.notification.util

enum class ReminderType(
    val channelId: String,
    val channelName: String,
    val channelDesc: String
) {
    WATERING(
        channelId = "watering_reminder",
        channelName = "Penyiraman",
        channelDesc = "Reminder penyiraman tanaman"
    ),
    WATER_CAPACITY(
        channelId = "water_capacity_reminder",
        channelName = "Kapasitas Air",
        channelDesc = "Peringatan kapasitas air"
    ),
    DUMMY(
        channelId = "dummy",
        channelName = "Dummy",
        channelDesc = ""
    )
}