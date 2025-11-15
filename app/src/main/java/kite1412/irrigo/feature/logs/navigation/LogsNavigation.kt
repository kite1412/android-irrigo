package kite1412.irrigo.feature.logs.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import kite1412.irrigo.feature.logs.LogsScreen
import kite1412.irrigo.feature.logs.util.LogsGroupType
import kotlinx.serialization.Serializable

@Serializable
data class LogsRoute(
    val selectedGroupOrdinal: Int? = null
)

fun NavController.navigateToLogs(logsGroup: LogsGroupType? = null) =
    navigate(LogsRoute(logsGroup?.ordinal))

fun NavGraphBuilder.logsScreen() {
    composable<LogsRoute> {
        LogsScreen()
    }
}