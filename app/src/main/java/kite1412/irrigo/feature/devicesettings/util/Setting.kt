package kite1412.irrigo.feature.devicesettings.util

enum class Setting(val displayName: String) {
    WATERING_AUTOMATED("Otomatis"),
    WATERING_MIN_SOIL_MOISTURE("Minimum Kelembaban Tanah"),
    WATERING_REMINDER("Reminder Penyiraman"),
    WATERING_DURATION("Durasi Penyiraman"),
    WATER_CAPACITY_REMINDER("Reminder Isi Ulang"),
    WATER_CAPACITY_MIN_CAPACITY("Minimum Kapasitas Air")
}