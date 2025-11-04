package kite1412.irrigo

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.remember
import androidx.navigation.NavController
import androidx.navigation.NavDestination.Companion.hasRoute
import kite1412.irrigo.feature.dashboard.navigation.DashboardRoute

@Composable
fun rememberIrrigoAppState(navController: NavController) =
    remember(navController) {
        IrrigoAppState(navController)
    }

class IrrigoAppState(
    private val navController: NavController
) {
    val routes = listOf(
        DashboardRoute::class to "Dashboard"
    )

    val currentRouteString: String?
        @Composable get() {
            val currentBackStackEntry = navController.currentBackStackEntryFlow
                .collectAsState(null)

            return routes.firstOrNull {
                currentBackStackEntry
                    .value
                    ?.destination
                    ?.hasRoute(it.first) == true
            }?.second
        }
}