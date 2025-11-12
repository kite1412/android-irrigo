package kite1412.irrigo.designsystem.component

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import kite1412.irrigo.designsystem.theme.DarkGray

@Composable
fun Toggle(
    checked: Boolean,
    onCheckedChange: (Boolean) -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    buttonSize: Dp = 32.dp
) {
    val animatedBackground by animateColorAsState(
        targetValue = if (checked) MaterialTheme.colorScheme.primary
        else DarkGray
    )
    val offsetX by animateDpAsState(
        targetValue = if (checked) 32.dp else 0.dp
    )
    val padding = 4.dp

    Box(
        modifier = modifier
            .size(
                height = buttonSize + padding * 2,
                width = buttonSize * 2 + padding * 2
            )
            .clip(CircleShape)
            .background(animatedBackground)
            .clickable(
                indication = null,
                interactionSource = null,
                enabled = enabled
            ) { onCheckedChange(!checked) }
            .padding(
                vertical = padding,
                horizontal = padding
            )
    ) {
        Box(
            Modifier
                .offset {
                    IntOffset(offsetX.toPx().toInt(), 0)
                }
                .size(buttonSize)
                .clip(CircleShape)
                .background(Color.White)
        )
    }
}