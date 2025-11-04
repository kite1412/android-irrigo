package kite1412.irrigo

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import kite1412.irrigo.designsystem.component.IrrigoAppBar
import kite1412.irrigo.feature.dashboard.navigation.DashboardRoute
import kite1412.irrigo.feature.dashboard.navigation.dashboardScreen

@Composable
fun IrrigoNavHost(
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController(),
    appState: IrrigoAppState = rememberIrrigoAppState(navController)
) {
    Scaffold(
        currentRoute = appState.currentRouteString ?: "",
        modifier = modifier
    ) {
        NavHost(
            navController = navController,
            startDestination = DashboardRoute,
            modifier = Modifier.fillMaxSize()
        ) {
            dashboardScreen()
        }
    }
}

@Composable
private fun Scaffold(
    currentRoute: String,
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit
) {
    Box(
        modifier = modifier
    ) {
        Column(
            modifier = Modifier.fillMaxSize()
        ) {
            IrrigoAppBar(
                title = currentRoute
            )
            content()
        }
    }
}