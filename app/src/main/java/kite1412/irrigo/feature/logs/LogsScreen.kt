package kite1412.irrigo.feature.logs

import androidx.activity.compose.BackHandler
import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import kite1412.irrigo.designsystem.theme.LightPastelBlue
import kite1412.irrigo.designsystem.theme.PastelBlue
import kite1412.irrigo.feature.logs.util.LogsGroupType

@Composable
fun LogsScreen(
    modifier: Modifier = Modifier,
    viewModel: LogsViewModel = hiltViewModel()
) {
    val selectedLogsGroup = viewModel.selectedLogsGroup

    BackHandler(
        enabled = selectedLogsGroup != null
    ) {
        viewModel.updateSelectedLogsGroup(null)
    }
    AnimatedContent(
        targetState = selectedLogsGroup,
        transitionSpec = {
            if (targetState == null)
                slideInHorizontally { -it } + fadeIn() togetherWith
                        slideOutHorizontally { it } + fadeOut()
            else slideInHorizontally { it } + fadeIn() togetherWith
                    slideOutHorizontally { -it } + fadeOut()
        }
    ) {
        when (it) {
            null -> Column(
                modifier = modifier,
                verticalArrangement = Arrangement.spacedBy(24.dp)
            ) {
                Text(
                    text = "Log / Catatan",
                    style = MaterialTheme.typography.bodyLarge.copy(
                        fontWeight = FontWeight.Bold
                    )
                )
                Column(
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    LogsGroup(
                        type = LogsGroupType.SOIL_MOISTURE,
                        background = MaterialTheme.colorScheme.primary,
                        onClick = viewModel::updateSelectedLogsGroup
                    )
                    LogsGroup(
                        type = LogsGroupType.WATERING,
                        background = LightPastelBlue,
                        onClick = viewModel::updateSelectedLogsGroup
                    )
                    LogsGroup(
                        type = LogsGroupType.WATER_CAPACITY,
                        background = PastelBlue,
                        onClick = viewModel::updateSelectedLogsGroup
                    )
                }
            }
            else -> Box(Modifier.fillMaxSize()) {
                Text(selectedLogsGroup?.string ?: "")
            }
        }
    }
}

@Composable
private fun LogsGroup(
    type: LogsGroupType,
    background: Color,
    onClick: (LogsGroupType) -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .shadow(
                elevation = 4.dp,
                shape = RoundedCornerShape(16.dp)
            )
            .background(background)
            .clickable(
                indication = null,
                interactionSource = null
            ) { onClick(type) }
            .padding(
                vertical = 16.dp,
                horizontal = 24.dp
            ),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        CompositionLocalProvider(
            LocalContentColor provides MaterialTheme.colorScheme.onBackground
        ) {
            val textStyle = LocalTextStyle.current.copy(
                fontWeight = FontWeight.Bold,
                fontStyle = FontStyle.Italic
            )

            Text(
                text = type.string,
                style = textStyle
            )
            Icon(
                painter = painterResource(type.iconId),
                contentDescription = type.name,
                modifier = Modifier.size((textStyle.fontSize.value * 2f).dp)
            )
        }
    }
}