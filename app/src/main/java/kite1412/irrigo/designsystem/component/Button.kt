package kite1412.irrigo.designsystem.component

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.sizeIn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import kite1412.irrigo.designsystem.theme.DarkGray
import kite1412.irrigo.designsystem.theme.Gray

@Composable
fun Button(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true
) {
    Box(
        modifier = modifier
            .sizeIn(
                minWidth = 80.dp,
                minHeight = 40.dp
            )
            .clip(RoundedCornerShape(8.dp))
            .clickable(
                enabled = enabled,
                onClick = onClick
            )
            .background(
                if (enabled) MaterialTheme.colorScheme.primary
                else DarkGray
            )
            .padding(
                horizontal = 16.dp,
                vertical = 8.dp
            )
    ) {
        Text(
            text = text,
            fontWeight = FontWeight.Bold,
            color = if (enabled) MaterialTheme.colorScheme.onBackground
            else Gray,
            modifier = Modifier.align(Alignment.Center)
        )
    }
}