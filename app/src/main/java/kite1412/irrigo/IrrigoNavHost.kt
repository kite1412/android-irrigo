package kite1412.irrigo

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
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
import kite1412.irrigo.feature.devicesettings.navigation.deviceSettingsScreen
import kite1412.irrigo.feature.devicesettings.navigation.navigateToDeviceSettings
import kite1412.irrigo.feature.devicesettings.util.Setting
import kite1412.irrigo.feature.logs.navigation.logsScreen
import kite1412.irrigo.feature.logs.navigation.navigateToLogs
import kite1412.irrigo.feature.logs.util.LogsGroupType
import kite1412.irrigo.util.Destination

@Composable
fun IrrigoNavHost(
    showAppBar: Boolean,
    showNavBar: Boolean,
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController(),
    appState: IrrigoAppState = rememberIrrigoAppState(navController),
    appBarSubtitle: String? = null
) {
    val currentDestination = appState.currentDestination

    Scaffold(
        selectedDestination = currentDestination,
        destinations = appState.destinations,
        onDestinationSelected = appState::navigateTo,
        showAppBar = showAppBar,
        showNavBar = showNavBar,
        modifier = modifier,
        appBarSubtitle = appBarSubtitle
    ) {
        NavHost(
            navController = navController,
            startDestination = DashboardRoute,
            modifier = Modifier.fillMaxSize()
        ) {
            dashboardScreen(
                onSoilMoistureSettingClick = {
                    navController.navigateToDeviceSettings(Setting.WATERING_MIN_SOIL_MOISTURE)
                },
                onAutomatedWateringSettingClick = {
                    navController.navigateToDeviceSettings(Setting.WATERING_AUTOMATED)
                },
                onMoreWateringLogClick = {
                    navController.navigateToLogs(LogsGroupType.WATERING)
                }
            )
            logsScreen()
            deviceSettingsScreen()
        }
    }
}

@Composable
private fun Scaffold(
    selectedDestination: Destination?,
    destinations: List<Destination>,
    onDestinationSelected: (Destination) -> Unit,
    showAppBar: Boolean,
    showNavBar: Boolean,
    modifier: Modifier = Modifier,
    appBarSubtitle: String? = null,
    content: @Composable () -> Unit
) {
    Box(
        modifier = modifier
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(32.dp)
        ) {
            AnimatedVisibility(
                visible = showAppBar
            ) {
                IrrigoAppBar(
                    title = selectedDestination?.name ?: "",
                    subtitle = appBarSubtitle
                )
            }
            content()
        }
        if (destinations.isNotEmpty()) AnimatedVisibility(
            visible = showNavBar,
            modifier = Modifier.align(Alignment.BottomEnd),
            enter = slideInVertically { -it } + fadeIn(),
            exit = slideOutVertically { it } + fadeOut()
        ) {
            IrrigoNavBar(
                destinations = destinations,
                onDestinationSelected = onDestinationSelected,
                selectedDestination = selectedDestination ?: destinations.first()
            )
        }
    }
}