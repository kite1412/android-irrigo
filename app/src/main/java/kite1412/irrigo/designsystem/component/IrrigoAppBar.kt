package kite1412.irrigo.designsystem.component

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.VerticalDivider
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import kite1412.irrigo.designsystem.theme.IrrigoTheme
import kite1412.irrigo.designsystem.util.IrrigoIcon
import kotlinx.coroutines.delay

@Composable
fun IrrigoAppBar(
    title: String,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        val titleSmall = MaterialTheme.typography.titleSmall

        Icon(
            painter = painterResource(IrrigoIcon.logoFull),
            contentDescription = "irrigo logo",
            modifier = Modifier.height((titleSmall.fontSize.value * 2).dp),
            tint = Color.Unspecified
        )
        VerticalDivider(
            modifier = Modifier
                .height(titleSmall.fontSize.value.dp)
                .clip(CircleShape),
            thickness = 2.dp,
            color = MaterialTheme.colorScheme.onBackground
        )
        AnimatedContent(
            targetState = title,
            transitionSpec = {
                slideInVertically { -it } + fadeIn() togetherWith
                        slideOutVertically { it } + fadeOut()
            }
        ) {
            Text(
                text = it,
                style = titleSmall.copy(
                    color = MaterialTheme.colorScheme.primary
                ),
                maxLines = 1
            )
        }
    }
}

@Preview
@Composable
private fun IrrigoAppBarPreview() {
    var title by remember { mutableStateOf("") }

    LaunchedEffect(title) {
        delay(1000)
        title += "A"
    }
    IrrigoTheme {
        IrrigoAppBar(title)
    }
}