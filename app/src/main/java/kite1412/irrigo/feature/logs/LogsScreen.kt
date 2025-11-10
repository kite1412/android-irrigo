package kite1412.irrigo.feature.logs

import androidx.activity.compose.BackHandler
import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
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
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import kite1412.irrigo.designsystem.theme.LightPastelBlue
import kite1412.irrigo.designsystem.theme.PastelBlue
import kite1412.irrigo.feature.logs.util.LogsGroupType
import kite1412.irrigo.model.SoilMoistureLog
import kite1412.irrigo.util.getLocalInstantInfo
import kite1412.irrigo.util.toLocalDateTime

@Composable
fun LogsScreen(
    modifier: Modifier = Modifier,
    viewModel: LogsViewModel = hiltViewModel()
) {
    val selectedLogsGroup = viewModel.selectedLogsGroup
    val soilMoistureLogs = viewModel.soilMoistureLogs
    val fetchingLogs = viewModel.fetchingLogs
    val selectedDay = viewModel.selectedDay

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
        if (it == null) Column(
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
        } else if (fetchingLogs) Text(
            text = "Mencari logs...",
            style = LocalTextStyle.current.copy(
                fontStyle = FontStyle.Italic
            )
        ) else when (it) {
            LogsGroupType.SOIL_MOISTURE -> SoilMoistureLogs(
                soilMoistureLogs
                    .filter {
                        it.timestamp.toLocalDateTime().date == selectedDay?.toLocalDateTime()?.date
                    }
            )
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

@Composable
private fun SoilMoistureLogs(
    logs: List<SoilMoistureLog>,
    modifier: Modifier = Modifier
) = Table(
    columns = listOf("Kelembaban Tanah", "Waktu"),
    rows = logs.map {
        listOf(
            String.format(
                locale = null,
                format = "%.1f",
                it.moisturePercent
            ),
            it.timestamp.getLocalInstantInfo(
                dateFormat = "d MM yyyy",
                timeFormat = "HH:mm"
            ).time
        )
    },
    keys = { logs.getOrNull(it)?.id ?: it },
    modifier = modifier,
    rowStyles = MaterialTheme.typography.bodySmall.run {
        listOf(
            copy(fontWeight = FontWeight.Bold),
            this
        )
    }
)

@Composable
private fun Table(
    columns: List<String>,
    rows: List<List<Any>>,
    keys: (Int) -> Any,
    modifier: Modifier = Modifier,
    rowStyles: List<TextStyle> = columns.map {
        LocalTextStyle.current
    }
) {
    require(columns.size == rowStyles.size) {
        "columns and columnStyles must have the same size"
    }

    Column(
        modifier = modifier.fillMaxWidth()
    ) {
        val onBackground = MaterialTheme.colorScheme.onBackground

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .clip(
                    RoundedCornerShape(
                        topStart = 16.dp,
                        topEnd = 16.dp
                    )
                )
                .background(onBackground)
                .padding(
                    vertical = 8.dp
                ),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            val style = MaterialTheme.typography.bodySmall.copy(
                color = MaterialTheme.colorScheme.primary,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center
            )

            columns.forEach {
                Text(
                    text = it,
                    modifier = Modifier.weight(1f),
                    style = style
                )
            }
        }

        val shape = RoundedCornerShape(
            bottomStart = 16.dp,
            bottomEnd = 16.dp
        )

        LazyColumn(
            modifier = Modifier
                .border(
                    width = 2.dp,
                    color = onBackground,
                    shape = shape
                )
                .clip(shape)
        ) {
            if (rows.isNotEmpty()) items(
                count = rows.size,
                key = keys
            ) { i ->
                val data = rows[i]

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(
                            if (i % 2 == 1) MaterialTheme.colorScheme.primary
                            else Color.Transparent
                        )
                        .padding(
                            vertical = 4.dp
                        ),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    data.forEachIndexed { i, d ->
                        Text(
                            text = d.toString(),
                            modifier = Modifier.weight(1f),
                            style = rowStyles[i].copy(
                                textAlign = TextAlign.Center,
                                color = onBackground
                            )
                        )
                    }
                }
            } else item {
                Text(
                    text = "Tidak ada log yang tersedia",
                    modifier = Modifier.align(Alignment.CenterHorizontally),
                    style = LocalTextStyle.current.copy(
                        fontStyle = FontStyle.Italic
                    )
                )
            }
        }
    }
}