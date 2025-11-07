package kite1412.irrigo.feature.dashboard.util

sealed interface DashboardUiEvent {
    data class ShowSnackbar(val message: String) : DashboardUiEvent
}