package kite1412.irrigo.ui.util

import androidx.compose.foundation.background
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import kite1412.irrigo.designsystem.theme.PaleBlue

@Composable
fun Modifier.screenBackground() =
    background(
        brush = Brush.linearGradient(
            colors = listOf(
                MaterialTheme.colorScheme.background,
                PaleBlue
            )
        )
    )