package kite1412.irrigo

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.remember
import androidx.navigation.NavController
import androidx.navigation.NavDestination.Companion.hasRoute
import androidx.navigation.navOptions
import kite1412.irrigo.designsystem.util.IrrigoIcon
import kite1412.irrigo.feature.dashboard.navigation.DashboardRoute
import kite1412.irrigo.feature.devicesettings.navigation.DeviceSettingsRoute
import kite1412.irrigo.feature.logs.navigation.LogsRoute
import kite1412.irrigo.util.Destination

@Composable
fun rememberIrrigoAppState(navController: NavController) =
    remember(navController) {
        IrrigoAppState(navController)
    }

class IrrigoAppState(
    private val navController: NavController
) {
    val destinations = listOf(
        Destination(
            route = DashboardRoute,
            iconId = IrrigoIcon.chart,
            name = "Dashboard"
        ),
        Destination(
            route = LogsRoute(null),
            iconId = IrrigoIcon.clipboard,
            name = "Log"
        ),
        Destination(
            route = DeviceSettingsRoute(null),
            iconId = IrrigoIcon.sliders,
            name = "Atur Perangkat"
        )
    )

    val currentDestination: Destination?
        @Composable get() {
            val currentBackStackEntry = navController.currentBackStackEntryFlow
                .collectAsState(null)

            return destinations.firstOrNull {
                currentBackStackEntry
                    .value
                    ?.destination
                    ?.hasRoute(it.route::class) == true
            }
        }

    fun navigateTo(destination: Destination) {
        navController.navigate(
            route = destination.route,
            navOptions = navOptions {
                launchSingleTop = true
                popUpTo(DashboardRoute)
            }
        )
    }
}