package kite1412.irrigo.ui.compositionlocal

import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.compositionLocalOf

val LocalSnackbarHostState = compositionLocalOf {
    SnackbarHostState()
}