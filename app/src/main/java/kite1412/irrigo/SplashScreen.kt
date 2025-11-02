package kite1412.irrigo

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.fadeIn
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kite1412.irrigo.designsystem.theme.Gray
import kite1412.irrigo.designsystem.theme.IrrigoTheme
import kite1412.irrigo.designsystem.theme.Quicksand
import kite1412.irrigo.designsystem.util.IrrigoIcon
import kite1412.irrigo.ui.util.screenBackground
import kotlinx.coroutines.delay

@Composable
fun SplashScreen(
    onComplete: () -> Unit,
    modifier: Modifier = Modifier
) {
    val onComplete by rememberUpdatedState(onComplete)
    var showText by rememberSaveable {
        mutableStateOf(false)
    }

    LaunchedEffect(Unit) {
        delay(500L)
        showText = true
        // arbitrary delay value
        delay(500L)
        onComplete()
    }
    Box(
        modifier = modifier
            .fillMaxSize()
            .screenBackground()
    ) {
        Row(
            modifier = Modifier.align(Alignment.Center),
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            val iconSize = 40
            val primary = MaterialTheme.colorScheme.primary
            val animatedIconSize by animateDpAsState(
                targetValue = if (showText) iconSize.dp else (iconSize * 1.5f).dp
            )

            Icon(
                painter = painterResource(IrrigoIcon.logoLeaf),
                contentDescription = "leaf logo",
                modifier = Modifier.size(animatedIconSize),
                tint = primary
            )
            AnimatedVisibility(
                visible = showText
            ) {
                Text(
                    text = buildAnnotatedString {
                        append("Irri")
                        withStyle(
                            style = SpanStyle(color = primary)
                        ) {
                            append("go")
                        }
                    },
                    style = TextStyle(
                        fontFamily = Quicksand,
                        fontWeight = FontWeight.Bold,
                        fontSize = (iconSize * 1.2f).sp
                    )
                )
            }
        }
        AnimatedVisibility(
            visible = showText,
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(bottom = 32.dp),
            enter = fadeIn()
        ) {
            Text(
                text = "Smart Irrigation Monitoring & Control",
                style = MaterialTheme.typography.bodySmall.copy(
                    color = Gray
                )
            )
        }
    }
}

@Preview
@Composable
private fun SplashScreenPreview() {
    IrrigoTheme {
        SplashScreen(
            onComplete = {}
        )
    }
}