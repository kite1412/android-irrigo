package kite1412.irrigo.feature.devicesettings.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import kite1412.irrigo.feature.devicesettings.DeviceSettingsScreen
import kite1412.irrigo.feature.devicesettings.util.Setting
import kotlinx.serialization.Serializable

@Serializable
data class DeviceSettingsRoute(
    val highlightSettingOrdinal: Int?
)

fun NavController.navigateToDeviceSettings(highlightSetting: Setting? = null) =
    navigate(DeviceSettingsRoute(highlightSetting?.ordinal))

fun NavGraphBuilder.deviceSettingsScreen() {
    composable<DeviceSettingsRoute> {
        DeviceSettingsScreen()
    }
}