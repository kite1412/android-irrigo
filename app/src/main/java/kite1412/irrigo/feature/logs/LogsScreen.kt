package kite1412.irrigo.feature.logs

import androidx.activity.compose.BackHandler
import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.FlowRowScope
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MenuDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
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
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.compose.LocalLifecycleOwner
import kite1412.irrigo.designsystem.theme.Gray
import kite1412.irrigo.designsystem.theme.LightPastelBlue
import kite1412.irrigo.designsystem.theme.PastelBlue
import kite1412.irrigo.designsystem.util.IrrigoIcon
import kite1412.irrigo.feature.logs.util.LogsGroupType
import kite1412.irrigo.model.SoilMoistureLog
import kite1412.irrigo.model.WateringLog
import kite1412.irrigo.ui.component.DeviceSelect
import kite1412.irrigo.ui.compositionlocal.LocalAppBarUpdater
import kite1412.irrigo.util.getLocalInstantInfo
import kite1412.irrigo.util.now
import kite1412.irrigo.util.toLocalDateTime
import kotlin.time.Instant

@Composable
fun LogsScreen(
    modifier: Modifier = Modifier,
    viewModel: LogsViewModel = hiltViewModel()
) {
    val selectedLogsGroup = viewModel.selectedLogsGroup
    val soilMoistureLogs = viewModel.soilMoistureLogs
    val wateringLogs = viewModel.wateringLogs
    val fetchingLogs = viewModel.fetchingLogs
    val selectedDate = viewModel.selectedDate
    val availableDates = viewModel.availableDates
    val device = viewModel.device
    val appBarUpdater = LocalAppBarUpdater.current
    val lifecycleOwner = LocalLifecycleOwner.current

    BackHandler(
        enabled = selectedLogsGroup != null
    ) {
        viewModel.updateSelectedLogsGroup(null)
        appBarUpdater.dismissSubtitle()
    }
    DisposableEffect(lifecycleOwner) {
        val observer = LifecycleEventObserver { _, event ->
            if (event == Lifecycle.Event.ON_STOP) {
                appBarUpdater.dismissSubtitle()
            }
        }
        lifecycleOwner.lifecycle.addObserver(observer)
        onDispose {
            lifecycleOwner.lifecycle.removeObserver(observer)
        }
    }
    if (device != null) AnimatedContent(
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
                    onClick = {
                        viewModel.updateSelectedLogsGroup(it)
                        appBarUpdater.setSubtitle("Kelembaban Tanah")
                    }
                )
                LogsGroup(
                    type = LogsGroupType.WATERING,
                    background = LightPastelBlue,
                    onClick = {
                        viewModel.updateSelectedLogsGroup(it)
                        appBarUpdater.setSubtitle("Penyiraman")
                    }
                )
                LogsGroup(
                    type = LogsGroupType.WATER_CAPACITY,
                    background = PastelBlue,
                    onClick = {
                        viewModel.updateSelectedLogsGroup(it)
                        appBarUpdater.setSubtitle("Kapasitas Air")
                    }
                )
            }
        } else if (fetchingLogs) Text(
            text = "Mencari logs...",
            style = LocalTextStyle.current.copy(
                fontStyle = FontStyle.Italic
            )
        ) else Column(
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            DeviceSelect(
                selectedDevice = device,
                devices = viewModel.devices,
                onDeviceChange = viewModel::onDeviceChange
            )
            when (it) {
                LogsGroupType.SOIL_MOISTURE -> SoilMoistureLogs(
                    logs = soilMoistureLogs
                        .filter { l ->
                            l.timestamp.toLocalDateTime().date == selectedDate?.toLocalDateTime()?.date
                        },
                    selectedDate = selectedDate ?: now(),
                    availableDates = availableDates,
                    onDateChange = viewModel::updateSelectedDate
                )
                LogsGroupType.WATERING -> WateringLogs(
                    logs = wateringLogs
                        .filter { l ->
                            l.timestamp.toLocalDateTime().date == selectedDate?.toLocalDateTime()?.date
                        },
                    selectedDate = selectedDate ?: now(),
                    availableDates = availableDates,
                    onDateChange = viewModel::updateSelectedDate
                )
                else -> Box(Modifier.fillMaxSize()) {
                    Text(selectedLogsGroup?.string ?: "")
                }
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
    selectedDate: Instant,
    availableDates: List<Instant>,
    onDateChange: (Instant) -> Unit,
    modifier: Modifier = Modifier
) = DateFilterableTable(
    columns = listOf("Kelembaban Tanah", "Waktu"),
    rows = logs.map {
        listOf(
            "${
                String.format(
                    locale = null,
                    format = "%.1f",
                    it.moisturePercent
                )
            } %",
            it.timestamp.getLocalInstantInfo(
                timeFormat = "HH:mm:ss"
            ).time
        )
    },
    keys = { logs.getOrNull(it)?.id ?: it },
    selectedDate = selectedDate,
    availableDates = availableDates,
    onDateChange = onDateChange,
    modifier = modifier,
    rowStyles = MaterialTheme.typography.bodySmall.run {
        listOf(
            copy(fontWeight = FontWeight.Bold),
            this
        )
    }
)

@Composable
private fun WateringLogs(
    logs: List<WateringLog>,
    selectedDate: Instant,
    availableDates: List<Instant>,
    onDateChange: (Instant) -> Unit,
    modifier: Modifier = Modifier
) {
    val normal = MaterialTheme.typography.bodySmall
    val bold = normal.copy(
        fontWeight = FontWeight.Bold
    )
    var status by rememberSaveable { mutableStateOf("Semua") }

    DateFilterableTable(
        columns = listOf("Durasi (Detik)", "Jenis", "Volume Air (Liter)", "Waktu"),
        rows = logs
            .filter {
                when (status) {
                    "Manual" -> it.manual
                    "Otomatis" -> !it.manual
                    else -> true
                }
            }
            .map {
                listOf(
                    it.durationMs / 1000f,
                    if (it.manual) "Manual" else "Otomatis",
                    it.waterVolumeLiters,
                    it.timestamp.getLocalInstantInfo(
                        timeFormat = "HH:mm:ss"
                    ).time
                )
            },
        keys = { logs.getOrNull(it)?.id ?: it },
        selectedDate = selectedDate,
        availableDates = availableDates,
        onDateChange = onDateChange,
        modifier = modifier,
        rowStyles = listOf(bold, bold, normal, normal),
        controls = {
            Control(
                selected = status,
                options = listOf("Semua", "Otomatis", "Manual"),
                onChange = { status = it },
                selectedText = { it },
                optionText = { it }
            )
        }
    )
}

@Composable
private fun DateFilterableTable(
    columns: List<String>,
    rows: List<List<Any>>,
    keys: (Int) -> Any,
    selectedDate: Instant,
    availableDates: List<Instant>,
    onDateChange: (Instant) -> Unit,
    modifier: Modifier = Modifier,
    rowStyles: List<TextStyle> = columns.map {
        LocalTextStyle.current
    },
    controls: (@Composable FlowRowScope.() -> Unit)? = null
) {
    Column(
        modifier = modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(8.dp),
        horizontalAlignment = Alignment.End
    ) {
        FlowRow(
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            controls?.invoke(this)
            DateSelect(
                selectedDate = selectedDate,
                availableDates = availableDates,
                onDateChange = onDateChange
            )
        }
        Table(
            columns = columns,
            rows = rows,
            keys = keys,
            rowStyles = rowStyles
        )
    }
}

@Composable
private fun DateSelect(
    selectedDate: Instant,
    availableDates: List<Instant>,
    onDateChange: (Instant) -> Unit,
    modifier: Modifier = Modifier
) = Control(
    selected = selectedDate,
    options = availableDates,
    onChange = onDateChange,
    selectedText = {
        it.getLocalInstantInfo(
            dateFormat = "d MMM yyyy"
        ).date
    },
    optionText = {
        it.getLocalInstantInfo(
            dateFormat = "d MMM yyyy"
        ).date
    },
    modifier = modifier
)

@Composable
private fun <T> Control(
    selected: T,
    options: List<T>,
    onChange: (T) -> Unit,
    selectedText: (T) -> String,
    optionText: (T) -> String,
    modifier: Modifier = Modifier
) {
    var showOptions by remember { mutableStateOf(false) }

    Box(
        modifier = modifier
    ) {
        Row(
            modifier = Modifier
                .clickable(
                    indication = null,
                    interactionSource = null
                ) { showOptions = !showOptions },
            verticalAlignment = Alignment.CenterVertically
        ) {
            CompositionLocalProvider(
                LocalContentColor provides MaterialTheme.colorScheme.primary
            ) {
                Text(
                    text = selectedText(selected),
                    style = LocalTextStyle.current.copy(
                        fontWeight = FontWeight.Bold,
                        fontStyle = FontStyle.Italic
                    )
                )
                Icon(
                    painter = painterResource(IrrigoIcon.chevronDown),
                    contentDescription = "select date"
                )
            }
        }
        DropdownMenu(
            expanded = showOptions,
            onDismissRequest = { showOptions = false },
            containerColor = MaterialTheme.colorScheme.background,
            border = BorderStroke(
                width = 2.dp,
                color = MaterialTheme.colorScheme.primary
            ),
            shape = RoundedCornerShape(16.dp)
        ) {
            val style = LocalTextStyle.current.copy(
                fontWeight = FontWeight.Bold
            )

            options.forEach {
                DropdownMenuItem(
                    text = {
                        Text(
                            text = optionText(it),
                            style = style
                        )
                    },
                    onClick = {
                        onChange(it)
                        showOptions = false
                    },
                    contentPadding = PaddingValues(
                        horizontal = 16.dp,
                        vertical = 8.dp
                    ),
                    enabled = selected != it,
                    colors = MenuDefaults.itemColors(
                        textColor = MaterialTheme.colorScheme.primary,
                        disabledTextColor = Gray
                    )
                )
            }
        }
    }
}

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
                .padding(8.dp),
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