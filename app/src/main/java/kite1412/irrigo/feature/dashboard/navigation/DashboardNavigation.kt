package kite1412.irrigo.feature.dashboard.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import kite1412.irrigo.feature.dashboard.DashboardScreen
import kotlinx.serialization.Serializable

@Serializable
data object DashboardRoute

fun NavGraphBuilder.dashboardScreen(
    onSoilMoistureSettingClick: () -> Unit
) {
    composable<DashboardRoute> {
        DashboardScreen(
            onSoilMoistureSettingClick = onSoilMoistureSettingClick
        )
    }
}