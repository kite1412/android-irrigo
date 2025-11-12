package kite1412.irrigo.feature.devicesettings.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import kite1412.irrigo.feature.devicesettings.DeviceSettingsScreen
import kotlinx.serialization.Serializable

@Serializable
data class DeviceSettingsRoute(
    val highlightSettingOrdinal: Int?
)

fun NavController.navigateToDeviceSettings(highlightSettingOrdinal: Int? = null) =
    navigate(DeviceSettingsRoute(highlightSettingOrdinal))

fun NavGraphBuilder.deviceSettingsScreen() {
    composable<DeviceSettingsRoute> {
        DeviceSettingsScreen()
    }
}