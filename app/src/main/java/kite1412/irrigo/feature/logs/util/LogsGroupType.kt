package kite1412.irrigo.feature.logs.util

import kite1412.irrigo.designsystem.util.IrrigoIcon

enum class LogsGroupType(val string: String, val iconId: Int) {
    SOIL_MOISTURE("Kelembaban Tanah", IrrigoIcon.plantWater),
    WATERING("Penyiraman", IrrigoIcon.waterSpray),
    WATER_CAPACITY("Kapasitas Air", IrrigoIcon.waterDrop)
}