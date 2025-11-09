package kite1412.irrigo

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import kite1412.irrigo.designsystem.component.IrrigoAppBar
import kite1412.irrigo.designsystem.component.IrrigoNavBar
import kite1412.irrigo.feature.dashboard.navigation.DashboardRoute
import kite1412.irrigo.feature.dashboard.navigation.dashboardScreen
import kite1412.irrigo.feature.logs.navigation.logsScreen
import kite1412.irrigo.util.Destination

@Composable
fun IrrigoNavHost(
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController(),
    appState: IrrigoAppState = rememberIrrigoAppState(navController)
) {
    val currentDestination = appState.currentDestination

    Scaffold(
        selectedDestination = currentDestination,
        destinations = appState.destinations,
        onDestinationSelected = appState::navigateTo,
        modifier = modifier
    ) {
        NavHost(
            navController = navController,
            startDestination = DashboardRoute,
            modifier = Modifier.fillMaxSize()
        ) {
            dashboardScreen(
                onSoilMoistureSettingClick = {}
            )
            logsScreen()
        }
    }
}

@Composable
private fun Scaffold(
    selectedDestination: Destination?,
    destinations: List<Destination>,
    onDestinationSelected: (Destination) -> Unit,
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit
) {
    Box(
        modifier = modifier
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(32.dp)
        ) {
            IrrigoAppBar(
                title = selectedDestination?.name ?: ""
            )
            content()
        }
        if (destinations.isNotEmpty()) IrrigoNavBar(
            destinations = destinations,
            onDestinationSelected = onDestinationSelected,
            selectedDestination = selectedDestination ?: destinations.first(),
            modifier = Modifier.align(Alignment.BottomEnd)
        )
    }
}