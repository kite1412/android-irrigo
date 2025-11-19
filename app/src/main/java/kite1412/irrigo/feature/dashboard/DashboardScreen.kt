package kite1412.irrigo.feature.dashboard

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.sizeIn
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Popup
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import kite1412.irrigo.designsystem.theme.DarkGray
import kite1412.irrigo.designsystem.theme.DarkPastelBlue
import kite1412.irrigo.designsystem.theme.Gray
import kite1412.irrigo.designsystem.theme.LightPastelBlue
import kite1412.irrigo.designsystem.theme.Orange
import kite1412.irrigo.designsystem.theme.Red
import kite1412.irrigo.designsystem.theme.Yellow
import kite1412.irrigo.designsystem.theme.bodyExtraSmall
import kite1412.irrigo.designsystem.util.IrrigoIcon
import kite1412.irrigo.feature.dashboard.util.DashboardUiEvent
import kite1412.irrigo.feature.dashboard.util.getWaterLevelPercentString
import kite1412.irrigo.model.LightIntensityLog
import kite1412.irrigo.model.LightIntensityStatus
import kite1412.irrigo.model.SoilMoistureLog
import kite1412.irrigo.model.WaterCapacityLog
import kite1412.irrigo.model.WaterContainer
import kite1412.irrigo.model.WateringLog
import kite1412.irrigo.ui.component.DeviceSelect
import kite1412.irrigo.ui.compositionlocal.LocalSnackbarHostState
import kite1412.irrigo.util.getLocalInstantInfo
import kotlinx.coroutines.delay
import kotlin.math.max

@Composable
fun DashboardScreen(
    onSoilMoistureSettingClick: () -> Unit,
    onAutomatedWateringSettingClick: () -> Unit,
    onMoreWateringLogClick: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: DashboardViewModel = hiltViewModel()
) {
    val device = viewModel.device
    val latestWaterCapacityLog by viewModel.latestWaterCapacityLog.collectAsStateWithLifecycle(null)
    val waterContainer = viewModel.waterContainer
    val latestWateringLogs = viewModel.latestWateringLogs
    val latestSoilMoistureLog by viewModel.latestSoilMoistureLog.collectAsStateWithLifecycle(null)
    val wateringConfig = viewModel.wateringConfig
    val latestLightIntensityLog by viewModel.latestLightIntensityLog.collectAsStateWithLifecycle(null)
    val snackbarHostState = LocalSnackbarHostState.current

    LaunchedEffect(Unit) {
        viewModel.uiEvent.collect {
            when (it) {
                is DashboardUiEvent.ShowSnackbar -> snackbarHostState
                    .showSnackbar(it.message)
            }
        }
    }
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
                Column(
                    modifier = Modifier.padding(
                        horizontal = 8.dp
                    ),
                    verticalArrangement = Arrangement.spacedBy(32.dp)
                ) {
                    DeviceControlSection(
                        waterContainer = waterContainer,
                        latestWaterCapacityLog = latestWaterCapacityLog,
                        latestWateringLogs = latestWateringLogs,
                        latestLightIntensityLog = latestLightIntensityLog,
                        onMoreWateringLog = onMoreWateringLogClick,
                        onWateringClick = viewModel::sendWateringSignal,
                        onAutomatedWateringClick = onAutomatedWateringSettingClick
                    )
                    SoilMoisture(
                        latest = latestSoilMoistureLog,
                        minPercentage = wateringConfig?.minSoilMoisturePercent,
                        onMinSettingClick = onSoilMoistureSettingClick
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
    latestLightIntensityLog: LightIntensityLog?,
    latestWateringLogs: List<WateringLog>?,
    onMoreWateringLog: () -> Unit,
    onWateringClick: () -> Unit,
    onAutomatedWateringClick: () -> Unit,
    modifier: Modifier = Modifier
) = Section(
    name = "Kontrol Perangkat",
    modifier = modifier
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(IntrinsicSize.Max),
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
                .fillMaxHeight(),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            LatestWateringLogs(
                latestLogs = latestWateringLogs,
                onMoreClick = onMoreWateringLog,
                modifier = Modifier.weight(5f)
            )
            WaterPlants(
                onClick = onWateringClick,
                modifier = Modifier
                    .weight(2f)
                    .fillMaxSize()
            )
        }
    }
    AutomatedWatering(
        onClick = onAutomatedWateringClick
    )
    LightIntensity(
        latest = latestLightIntensityLog
    )
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
        targetValue = if (noData || latest.currentHeightCm == 0.0) DarkGray else LightPastelBlue
    )
    var containerHeightPx by remember { mutableFloatStateOf(0f) }
    val progressHeight by animateFloatAsState(
        if (noData) 0f
        else (latest.currentHeightCm / waterContainer.heightCm)
            .toFloat()
            .coerceIn(0f, 1f) * max(1f, containerHeightPx)
    )
    val shape = RoundedCornerShape(16.dp)

    Box(
        modifier = modifier
            .aspectRatio(1f)
            .fillMaxWidth()
            .shadow(
                elevation = 4.dp,
                shape = shape
            )
            .onSizeChanged {
                containerHeightPx = it.height.toFloat()
            }
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
                            LocalTextStyle provides MaterialTheme.typography.bodyExtraSmall.copy(
                                fontStyle = FontStyle.Italic,
                                color = MaterialTheme.colorScheme.onBackground
                            )
                        ) {
                            Text(
                                text = "${i + 1}. ${instantInfo.date}",
                                modifier = Modifier.weight(8f)
                            )
                            Text(
                                text = instantInfo.time,
                                modifier = Modifier.weight(2f)
                            )
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
            text = "Mencari log penyiraman...",
            style = MaterialTheme.typography.bodySmall.copy(
                fontStyle = FontStyle.Italic,
                textAlign = TextAlign.Center
            )
        )
    }
}

@Composable
private fun WaterPlants(
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val shape = RoundedCornerShape(16.dp)

    Row(
        modifier = modifier
            .shadow(
                elevation = 4.dp,
                shape = shape
            )
            .background(MaterialTheme.colorScheme.primary)
            .padding(
                horizontal = 16.dp
            )
            .clickable(
                indication = null,
                interactionSource = null,
                onClick = onClick
            ),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(
            space = 8.dp,
            alignment = Alignment.End
        )
    ) {
        val textStyle = LocalTextStyle.current

        CompositionLocalProvider(
            LocalContentColor provides Color.White
        ) {
            Text(
                text = "Siram",
                style = textStyle.copy(
                    fontWeight = FontWeight.Bold
                )
            )
            Icon(
                painter = painterResource(IrrigoIcon.waterSpray),
                contentDescription = "siram",
                modifier = Modifier.size((textStyle.fontSize.value * 2f).dp)
            )
        }
    }
}

@Composable
private fun AutomatedWatering(
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    CompositionLocalProvider(
        LocalContentColor provides MaterialTheme.colorScheme.secondary
    ) {
        Row(
            modifier = modifier
                .fillMaxWidth()
                .border(
                    width = 2.dp,
                    shape = RoundedCornerShape(8.dp),
                    color = LocalContentColor.current
                )
                .clickable(
                    indication = null,
                    interactionSource = null,
                    onClick = onClick
                )
                .padding(
                    vertical = 8.dp,
                    horizontal = 16.dp
                ),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            val textStyle = LocalTextStyle.current

            Text(
                text = "Penyiraman Otomatis",
                style = textStyle.copy(
                    fontWeight = FontWeight.Bold
                )
            )
            Icon(
                painter = painterResource(IrrigoIcon.timerReset),
                contentDescription = null,
                modifier = Modifier.size((textStyle.fontSize.value * 2).dp)
            )
        }
    }
}

@Composable
private fun SoilMoisture(
    latest: SoilMoistureLog?,
    minPercentage: Double?,
    onMinSettingClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .border(
                width = 2.dp,
                color = MaterialTheme.colorScheme.onBackground,
                shape = RoundedCornerShape(16.dp)
            )
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Text(
            text = "Kelembaban Tanah",
            style = LocalTextStyle.current.copy(
                fontWeight = FontWeight.Bold
            )
        )
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(IntrinsicSize.Max),
            horizontalArrangement = Arrangement.spacedBy(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            SoilMoistureIndicator(
                min = minPercentage?.toFloat(),
                value = latest?.moisturePercent?.toFloat(),
                modifier = Modifier.weight(4f)
            )
            Column(
                modifier = Modifier
                    .weight(6f)
                    .fillMaxHeight(),
                verticalArrangement = Arrangement.SpaceBetween
            ) {
                Column(
                    verticalArrangement = Arrangement.spacedBy(4.dp)
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(4.dp)
                    ) {
                        SoilMoistureStatus(
                            status = "Batas Minimum",
                            color = Red
                        )
                        Box {
                            var showInfo by remember { mutableStateOf(false) }

                            Icon(
                                painter = painterResource(IrrigoIcon.about),
                                contentDescription = null,
                                modifier = Modifier
                                    .size(12.dp)
                                    .clickable(
                                        indication = null,
                                        interactionSource = null
                                    ) { showInfo = !showInfo },
                                tint = DarkGray
                            )
                            if (showInfo) Popup(
                                onDismissRequest = { showInfo = false }
                            ) {
                                val shape = RoundedCornerShape(16.dp)

                                Text(
                                    text = "Batas minimum kelembaban untuk penyiraman otomatis",
                                    modifier = Modifier
                                        .background(
                                            color = MaterialTheme.colorScheme.primary,
                                            shape = shape
                                        )
                                        .border(
                                            width = 1.dp,
                                            color = MaterialTheme.colorScheme.onBackground,
                                            shape = shape
                                        )
                                        .padding(16.dp)
                                        .sizeIn(
                                            maxWidth = 180.dp,
                                            maxHeight = 180.dp
                                        )
                                )
                            }
                        }
                    }
                    SoilMoistureStatus(
                        status = "Kelembaban",
                        color = MaterialTheme.colorScheme.primary
                    )
                    HorizontalDivider(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clip(CircleShape),
                        color = MaterialTheme.colorScheme.onBackground,
                        thickness = 2.dp
                    )
                    CompositionLocalProvider(
                        LocalTextStyle provides MaterialTheme.typography.bodyExtraSmall.copy(
                            color = Gray,
                            fontStyle = FontStyle.Italic
                        )
                    ) {
                        Text("Batas Minimum: ${minPercentage?.toInt() ?: 0}%")
                        Text(
                            "Kelembaban: ${
                                if (latest != null) "${String.format(
                                    locale = null,
                                    format = "%.1f",
                                    latest.moisturePercent
                                )}%" 
                                else "Mencari..."
                            }"
                        )
                    }
                }
                Row(
                    modifier = Modifier
                        .align(Alignment.End)
                        .clickable(
                            indication = null,
                            interactionSource = null,
                            onClick = onMinSettingClick
                        ),
                    horizontalArrangement = Arrangement.spacedBy(4.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    CompositionLocalProvider(
                        LocalContentColor provides MaterialTheme.colorScheme.secondary
                    ) {
                        val textStyle = MaterialTheme.typography.bodySmall.copy(
                            fontWeight = FontWeight.Bold
                        )

                        Text(
                            text = "Atur batas minimum",
                            style = textStyle
                        )
                        Icon(
                            painter = painterResource(IrrigoIcon.arrowRightLine),
                            contentDescription = null,
                            modifier = Modifier.size((textStyle.fontSize.value * 1.5f).dp)
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun SoilMoistureIndicator(
    min: Float?, // 0 - 100
    value: Float?, // 0 - 100
    modifier: Modifier = Modifier
) {
    var startAnimate by remember { mutableStateOf(false) }
    val minAnimatedValue by animateFloatAsState(
        if (startAnimate && min != null) min / 100f else 0f
    )
    val animatedValue by animateFloatAsState(
        if (startAnimate && value != null) value / 100f else 0f
    )
    val onBackground = MaterialTheme.colorScheme.onBackground
    val primary = MaterialTheme.colorScheme.primary

    LaunchedEffect(Unit) {
        delay(500)
        startAnimate = true
    }

    Box(
        modifier = modifier.aspectRatio(1f)
    ) {
        if (value != null && min != null) Canvas(Modifier.fillMaxSize()) {
            val style = Stroke(
                width = 8.dp.toPx()
            )

            drawArc(
                color = onBackground,
                startAngle = 0f,
                sweepAngle = 360f,
                useCenter = false,
                style = style
            )
            drawArc(
                color = if (value < min) Red else primary,
                startAngle = -90f,
                sweepAngle = 360 * if (value < min) minAnimatedValue else animatedValue,
                useCenter = false,
                style = style
            )
            drawArc(
                color = if (value < min) primary else Red,
                startAngle = -90f,
                sweepAngle = 360f * if (value < min) animatedValue else minAnimatedValue,
                useCenter = false,
                style = style
            )
        }
        Text(
            text = if (value != null) "${String.format(
                locale = null,
                format = "%.1f",
                value
            )}%" else "Mencari data...",
            modifier = Modifier.align(Alignment.Center),
            style = LocalTextStyle.current.copy(
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center
            )
        )
    }
}

@Composable
private fun SoilMoistureStatus(
    status: String,
    color: Color,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.spacedBy(4.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        val textStyle = MaterialTheme.typography.bodySmall

        Box(
            modifier = Modifier
                .size(textStyle.fontSize.value.dp)
                .clip(CircleShape)
                .background(color)
        )
        Text(
            text = status,
            style = textStyle
        )
    }
}

@Composable
private fun LightIntensity(
    latest: LightIntensityLog?,
    modifier: Modifier = Modifier
) {
    val color by animateColorAsState(
        when (latest?.status) {
            LightIntensityStatus.NORMAL -> Orange
            LightIntensityStatus.HIGH -> Yellow
            else -> Gray
        }
    )

    Row(
        modifier = modifier
            .fillMaxWidth()
            .border(
                width = 2.dp,
                color = color,
                shape = RoundedCornerShape(8.dp)
            )
            .padding(
                horizontal = 16.dp,
                vertical = 8.dp
            ),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column {
            Text(
                text = buildAnnotatedString {
                    if (latest != null) append("Intensitas Cahaya: ")
                    withStyle(
                        style = SpanStyle(
                            color = color,
                            fontWeight = FontWeight.Bold,
                            fontStyle = if (latest != null) FontStyle.Normal
                                else FontStyle.Italic
                        )
                    ) {
                        append(
                            when (latest?.status) {
                                LightIntensityStatus.NORMAL -> "Normal"
                                LightIntensityStatus.LOW -> "Rendah"
                                LightIntensityStatus.HIGH -> "Tinggi"
                                else -> "Mencari data..."
                            }
                        )
                    }
                }
            )
            Text(
                text = if (latest != null) "${latest.lux} Lux" else "",
                style = MaterialTheme.typography.bodySmall.copy(
                    fontStyle = FontStyle.Italic
                )
            )
        }
        Icon(
            painter = painterResource(IrrigoIcon.sun),
            contentDescription = null,
            modifier = Modifier.size(40.dp),
            tint = color
        )
    }
}