package kite1412.irrigo.ui.compositionlocal

import androidx.compose.runtime.compositionLocalOf
import androidx.compose.ui.unit.dp
import kite1412.irrigo.ui.util.ComponentSizeInfo

val LocalNavBarSizeInfo = compositionLocalOf {
    ComponentSizeInfo(0.dp, 0.dp)
}