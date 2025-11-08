package kite1412.irrigo.feature.dashboard

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.animateColor
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.animateDp
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.updateTransition
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.LocalWindowInfo
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.max
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import kite1412.irrigo.designsystem.theme.DarkGray
import kite1412.irrigo.designsystem.theme.DarkPastelBlue
import kite1412.irrigo.designsystem.theme.PastelBlue
import kite1412.irrigo.designsystem.util.IrrigoIcon
import kite1412.irrigo.feature.dashboard.util.getLocalInstantInfo
import kite1412.irrigo.feature.dashboard.util.getWaterLevelPercentString
import kite1412.irrigo.model.Device
import kite1412.irrigo.model.WaterCapacityLog
import kite1412.irrigo.model.WaterContainer
import kite1412.irrigo.model.WateringLog
import kotlin.math.max

@Composable
fun DashboardScreen(
    modifier: Modifier = Modifier,
    viewModel: DashboardViewModel = hiltViewModel()
) {
    val device = viewModel.device
    val latestWaterCapacityLog by viewModel.latestWaterCapacityLog.collectAsStateWithLifecycle(null)
    val waterContainer = viewModel.waterContainer
    val latestWateringLogs by viewModel.latestWateringLogs.collectAsStateWithLifecycle(null)

    AnimatedContent(
        targetState = device,
        modifier = modifier
    ) {
        if (it != null) LazyColumn(
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            item {
                DeviceSelect(
                    selectedDevice = it,
                    devices = viewModel.devices,
                    onDeviceChange = viewModel::onDeviceChange
                )
            }
            item {
                DeviceControlSection(
                    waterContainer = waterContainer,
                    latestWaterCapacityLog = latestWaterCapacityLog,
                    latestWateringLogs = latestWateringLogs,
                    onMoreWateringLog = {}
                )
            }
        }
    }
}

@Composable
private fun DeviceSelect(
    selectedDevice: Device,
    devices: List<Device>,
    onDeviceChange: (Device) -> Unit,
    modifier: Modifier = Modifier
) {
    var expanded by remember { mutableStateOf(false) }
    val cornerSize = 8.dp
    val transition = updateTransition(expanded)
    val background by transition.animateColor {
        if (it) MaterialTheme.colorScheme.onBackground else Color.Transparent
    }
    val contentColor by transition.animateColor {
        if (it) MaterialTheme.colorScheme.primary else Color.Black
    }
    val bottomCornersSize by transition.animateDp {
        if (it) 0.dp else cornerSize
    }
    val windowInfo = LocalWindowInfo.current
    val density = LocalDensity.current

    BoxWithConstraints(
        modifier = modifier
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(
                    color = background,
                    shape = RoundedCornerShape(
                        topStart = cornerSize,
                        topEnd = cornerSize,
                        bottomStart = bottomCornersSize,
                        bottomEnd = bottomCornersSize
                    )
                )
                .border(
                    width = 1.dp,
                    color = MaterialTheme.colorScheme.onBackground,
                    shape = RoundedCornerShape(
                        topStart = cornerSize,
                        topEnd = cornerSize,
                        bottomStart = bottomCornersSize,
                        bottomEnd = bottomCornersSize
                    )
                )
                .clickable {
                    expanded = !expanded
                }
                .padding(
                    vertical = 8.dp,
                    horizontal = 16.dp
                ),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = selectedDevice.name,
                style = LocalTextStyle.current.copy(
                    color = contentColor,
                    fontStyle = FontStyle.Italic
                )
            )
            Icon(
                painter = painterResource(IrrigoIcon.chevronDown),
                contentDescription = null,
                tint = contentColor
            )
        }

        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false },
            modifier = Modifier
                .width(maxWidth)
                .heightIn(
                    max = max(
                        a = 300.dp,
                        b = with(density) {
                            (windowInfo.containerSize.height / 3).toDp()
                        }
                    )
                ),
            border = BorderStroke(
                width = 2.dp,
                color = MaterialTheme.colorScheme.onBackground
            ),
            shape = RoundedCornerShape(
                bottomStart = 8.dp,
                bottomEnd = 8.dp
            ),
            containerColor = MaterialTheme.colorScheme.background,
            tonalElevation = 0.dp
        ) {
            devices.forEachIndexed { i, d ->
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable {
                            onDeviceChange(d)
                        }
                ) {
                    Text(
                        text = d.name,
                        modifier = Modifier.padding(8.dp)
                    )
                    if (i != devices.lastIndex) HorizontalDivider(
                        modifier = Modifier.fillMaxWidth(),
                        color = MaterialTheme.colorScheme.onBackground,
                        thickness = 2.dp
                    )
                }
            }
        }
    }
}

@Composable
private fun DeviceControlSection(
    waterContainer: WaterContainer?,
    latestWaterCapacityLog: WaterCapacityLog?,
    latestWateringLogs: List<WateringLog>?,
    onMoreWateringLog: () -> Unit,
    modifier: Modifier = Modifier
) = Section(
    name = "Kontrol Perangkat",
    modifier = modifier
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .height(IntrinsicSize.Max),
        verticalArrangement = Arrangement.spacedBy(32.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            WaterCapacity(
                latest = latestWaterCapacityLog,
                waterContainer = waterContainer,
                modifier = Modifier.weight(1f)
            )
            Column(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxHeight()
            ) {
                LatestWateringLogs(
                    latestLogs = latestWateringLogs,
                    onMoreClick = onMoreWateringLog,
                    modifier = Modifier.weight(4f)
                )
                Box(
                    modifier = Modifier
                        .weight(2f)
                        .background(Color.Black)
                )
            }
        }
    }
}

@Composable
private fun Section(
    name: String,
    modifier: Modifier = Modifier,
    content: @Composable ColumnScope.() -> Unit
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Text(
            text = name,
            style = MaterialTheme.typography.bodyLarge.copy(
                fontWeight = FontWeight.Bold
            )
        )
        content(this)
    }
}

@Composable
private fun WaterCapacity(
    latest: WaterCapacityLog?,
    waterContainer: WaterContainer?,
    modifier: Modifier = Modifier
) {
    val noData = latest == null || waterContainer == null
    val background by animateColorAsState(
        targetValue = if (noData || latest.currentHeightCm == 0.0) DarkGray else PastelBlue
    )
    var containerHeightPx by remember { mutableFloatStateOf(0f) }
    val progressHeight by animateFloatAsState(
        if (noData) 0f
        else (latest.currentHeightCm / waterContainer.heightCm)
            .toFloat()
            .coerceIn(0f, 1f) * max(1f, containerHeightPx)
    )

    Box(
        modifier = modifier
            .aspectRatio(1f)
            .fillMaxWidth()
            .onSizeChanged {
                containerHeightPx = it.height.toFloat()
            }
            .clip(RoundedCornerShape(16.dp))
            .background(background)
            .drawBehind {
                val offsetY = size.height - progressHeight
                val topLeft = Offset(
                    x = 0f,
                    y = offsetY
                )

                drawRect(
                    color = DarkPastelBlue,
                    size = size.copy(
                        height = progressHeight
                    ),
                    topLeft = topLeft
                )
                drawRect(
                    color = Color(0xFF68BEFF),
                    topLeft = topLeft,
                    size = size.copy(
                        height = 4.dp.toPx()
                    )
                )
            }
            .padding(16.dp)
    ) {
        CompositionLocalProvider(
            LocalContentColor provides Color.White
        ) {
            Text(
                text = "Kapasitas Air",
                style = LocalTextStyle.current.copy(
                    fontWeight = FontWeight.Bold
                )
            )
            Row(
                modifier = Modifier.align(Alignment.BottomEnd),
                verticalAlignment = Alignment.CenterVertically
            ) {
                val textStyle = MaterialTheme.typography.bodySmall.copy(
                    fontWeight = FontWeight.Bold
                )

                if (!noData) {
                    Text(
                        text = getWaterLevelPercentString(
                            heightCm = waterContainer.heightCm,
                            currentHeightCm = latest.currentHeightCm
                        ),
                        style = textStyle
                    )
                    Icon(
                        painter = painterResource(IrrigoIcon.waterDrop),
                        contentDescription = null,
                        modifier = Modifier.size(32.dp)
                    )
                } else Text(
                    text = "Mencari data...",
                    style = textStyle
                )
            }
        }
    }
}

@Composable
private fun LatestWateringLogs(
    latestLogs: List<WateringLog>?,
    onMoreClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .border(
                width = 2.dp,
                color = MaterialTheme.colorScheme.onBackground,
                shape = RoundedCornerShape(16.dp)
            )
            .clickable(
                indication = null,
                interactionSource = null,
                onClick = onMoreClick
            )
            .padding(8.dp),
        verticalArrangement = Arrangement.spacedBy(4.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            CompositionLocalProvider(
                LocalContentColor provides Color.Black
            ) {
                val textStyle = LocalTextStyle.current

                Text(
                    text = "Log Penyiraman",
                    style = textStyle.copy(
                        fontWeight = FontWeight.Bold
                    )
                )
                Icon(
                    painter = painterResource(IrrigoIcon.arrowRight),
                    contentDescription = "more",
                    modifier = Modifier.size((textStyle.fontSize.value * 1.4f).dp)
                )
            }
        }
        if (latestLogs != null) {
            if (latestLogs.isNotEmpty()) Column(
                verticalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                latestLogs.take(4).forEachIndexed { i, e ->
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        val instantInfo = remember(e.timestamp) {
                            e.timestamp.getLocalInstantInfo()
                        }

                        CompositionLocalProvider(
                            LocalTextStyle provides MaterialTheme.typography.bodySmall.copy(
                                fontStyle = FontStyle.Italic,
                                color = MaterialTheme.colorScheme.onBackground
                            )
                        ) {
                            Text("${i + 1}. ${instantInfo.day}")
                            Text(instantInfo.time)
                        }
                    }
                }
            } else Text(
                text = "Tidak ada log penyiraman.",
                style = MaterialTheme.typography.bodySmall.copy(
                    fontStyle = FontStyle.Italic,
                    color = DarkGray
                )
            )
        } else Text(
            text = "Mencari log penyiraman",
            style = MaterialTheme.typography.bodySmall.copy(
                fontStyle = FontStyle.Italic
            )
        )
    }
}