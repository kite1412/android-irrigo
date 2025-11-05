package kite1412.irrigo

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.remember
import androidx.navigation.NavController
import androidx.navigation.NavDestination.Companion.hasRoute
import kite1412.irrigo.designsystem.util.IrrigoIcon
import kite1412.irrigo.feature.dashboard.navigation.DashboardRoute
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
            route = DashboardRoute::class,
            iconId = IrrigoIcon.chart,
            name = "Dashboard"
        ),
        Destination(
            route = TempRoute::class,
            iconId = IrrigoIcon.clipboard,
            name = "Log"
        ),
        Destination(
            route = TempRoute::class,
            iconId = IrrigoIcon.sliders,
            name = "Pengaturan"
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
                    ?.hasRoute(it.route) == true
            }
        }

    // TODO implement logic
    fun navigateTo(destination: Destination) {

    }
}

// TODO delete
private object TempRoute