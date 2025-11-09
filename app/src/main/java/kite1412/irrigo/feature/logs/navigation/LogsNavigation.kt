package kite1412.irrigo.feature.logs.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import kite1412.irrigo.feature.logs.LogsScreen
import kotlinx.serialization.Serializable

@Serializable
data object LogsRoute

fun NavGraphBuilder.logsScreen() {
    composable<LogsRoute> {
        LogsScreen()
    }
}