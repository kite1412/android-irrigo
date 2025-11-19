package kite1412.irrigo.feature.devicesettings

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import kite1412.irrigo.designsystem.component.Button
import kite1412.irrigo.designsystem.component.TextField
import kite1412.irrigo.designsystem.component.Toggle
import kite1412.irrigo.designsystem.theme.DarkGray
import kite1412.irrigo.feature.devicesettings.util.Setting
import kotlinx.coroutines.delay

@Composable
fun DeviceSettingsScreen(
    modifier: Modifier = Modifier,
    viewModel: DeviceSettingsViewModel = hiltViewModel()
) {
    val wateringConfig = viewModel.wateringConfig
    val waterCapacityConfig = viewModel.waterCapacityConfig
    val highlightedSetting = viewModel.highlightedSetting

    if (wateringConfig != null && waterCapacityConfig != null) LazyColumn(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        item {
            WateringSection(
                isAutomated = viewModel.wateringConfig?.automated ?: false,
                onAutomatedChange = viewModel::updateAutomatedSetting,
                minSoilMoisture = wateringConfig.minSoilMoisturePercent,
                onMinSoilMoistureChange = viewModel::updateMinSoilMoisture,
                wateringReminder = viewModel.wateringReminder,
                onWateringReminderChange = viewModel::updateWateringReminder,
                wateringDurationMs = wateringConfig.durationMs,
                onWateringDurationChange = viewModel::updateWateringDuration,
                highlightedSetting = highlightedSetting
            )
        }
        item {
            WaterCapacitySection(
                waterCapacityReminder = viewModel.waterCapacityReminder,
                onWaterCapacityReminderChange = viewModel::updateWaterCapacityReminder,
                minCapacity = waterCapacityConfig.minWaterCapacityPercent,
                onMinCapacityChange = viewModel::updateMinWaterCapacity,
                highlightedSetting = highlightedSetting
            )
        }
    } else Box(
        modifier = modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = "Mencari data konfigurasi...",
            style = MaterialTheme.typography.bodyLarge.copy(
                fontWeight = FontWeight.Bold,
                fontStyle = FontStyle.Italic
            )
        )
    }
}

@Composable
private fun WateringSection(
    isAutomated: Boolean,
    onAutomatedChange: (Boolean) -> Unit,
    minSoilMoisture: Double,
    onMinSoilMoistureChange: (Double) -> Unit,
    wateringReminder: Boolean,
    onWateringReminderChange: (Boolean) -> Unit,
    wateringDurationMs: Int,
    onWateringDurationChange: (Int) -> Unit,
    modifier: Modifier = Modifier,
    highlightedSetting: Setting? = null
) = Section(
    name = "Penyiraman",
    modifier = modifier
) {
    Setting(
        setting = Setting.WATERING_AUTOMATED,
        highlight = highlightedSetting == Setting.WATERING_AUTOMATED
    ) {
        Toggle(
            checked = isAutomated,
            onCheckedChange = onAutomatedChange
        )
    }
    Setting(
        setting = Setting.WATERING_MIN_SOIL_MOISTURE,
        highlight = highlightedSetting == Setting.WATERING_MIN_SOIL_MOISTURE
    ) {
        TextEdit(
            value = String.format(
                locale = null,
                format = "%.1f",
                minSoilMoisture
            ),
            editTitle = "Ubah Minimum Kelembaban Tanah",
            onSave = { onMinSoilMoistureChange(it.toDouble()) },
            desc = "%",
            keyboardType = KeyboardType.Number
        )
    }
    Setting(
        setting = Setting.WATERING_REMINDER,
        highlight = highlightedSetting == Setting.WATERING_REMINDER
    ) {
        Toggle(
            checked = wateringReminder,
            onCheckedChange = onWateringReminderChange
        )
    }
    Setting(
        setting = Setting.WATERING_DURATION,
        highlight = highlightedSetting == Setting.WATERING_DURATION
    ) {
        TextEdit(
            value = (wateringDurationMs / 1000).toString(),
            editTitle = "Ubah Durasi Penyiraman (detik)",
            onSave = { onWateringDurationChange(it.toInt() * 1000) },
            desc = "Detik",
            keyboardType = KeyboardType.Number
        )
    }
}

@Composable
private fun WaterCapacitySection(
    waterCapacityReminder: Boolean,
    onWaterCapacityReminderChange: (Boolean) -> Unit,
    minCapacity: Double,
    onMinCapacityChange: (Double) -> Unit,
    modifier: Modifier = Modifier,
    highlightedSetting: Setting? = null
) = Section(
    name = "Kapasitas Air",
    modifier = modifier
) {
    Setting(
        setting = Setting.WATER_CAPACITY_REMINDER,
        subSettings = {
            SubSetting(
                setting = Setting.WATER_CAPACITY_MIN_CAPACITY,
                highlight = highlightedSetting == Setting.WATER_CAPACITY_MIN_CAPACITY
            ) {
                TextEdit(
                    value = String.format(
                        locale = null,
                        format = "%.1f",
                        minCapacity
                    ),
                    editTitle = "Ubah Minimum Kapasitas Air",
                    onSave = { onMinCapacityChange(it.toDouble()) },
                    desc = "%",
                    keyboardType = KeyboardType.Number
                )
            }
        },
        highlight = highlightedSetting == Setting.WATER_CAPACITY_REMINDER
    ) {
        Toggle(
            checked = waterCapacityReminder,
            onCheckedChange = onWaterCapacityReminderChange
        )
    }
}

@Composable
private fun Section(
    name: String,
    modifier: Modifier = Modifier,
    settings: @Composable ColumnScope.() -> Unit
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Text(
            text = name,
            style = MaterialTheme.typography.bodyLarge.copy(
                color = MaterialTheme.colorScheme.primary,
                fontWeight = FontWeight.Bold,
                fontStyle = FontStyle.Italic
            )
        )
        Level(
            content = settings
        )
    }
}

@Composable
private fun Level(
    modifier: Modifier = Modifier,
    content: @Composable ColumnScope.() -> Unit
) {
    Column(
        modifier = modifier.padding(start = 8.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp),
        content = content
    )
}

@Composable
private fun ColumnScope.Setting(
    setting: Setting,
    modifier: Modifier = Modifier,
    subSettings: (@Composable ColumnScope.() -> Unit)? = null,
    highlight: Boolean = false,
    action: @Composable RowScope.() -> Unit
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .background(settingBackground(highlight)),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = setting.displayName,
            style = LocalTextStyle.current.copy(
                fontWeight = FontWeight.Bold
            )
        )
        action(this)
    }
    if (subSettings != null) Level(
        content = subSettings
    )
    HorizontalDivider(
        modifier = Modifier
            .fillMaxWidth()
            .clip(CircleShape),
        thickness = 1.dp,
        color = MaterialTheme.colorScheme.onBackground
    )
}

@Composable
private fun ColumnScope.SubSetting(
    setting: Setting,
    modifier: Modifier = Modifier,
    highlight: Boolean = false,
    action: @Composable RowScope.() -> Unit
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .background(settingBackground(highlight)),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) { 
        Text(setting.displayName)
        action(this)
    }
}

@Composable
private fun settingBackground(highlighted: Boolean): Color =
    if (highlighted) {
        var start by rememberSaveable {
            mutableStateOf(false)
        }
        val bg by animateColorAsState(
            targetValue = if (start && highlighted) Color.Black.copy(
                alpha = 0.2f
            ) else Color.Transparent,
            animationSpec = tween(500)
        )

        LaunchedEffect(Unit) {
            if (!start && highlighted) {
                delay(200)
                start = true
                delay(700)
                start = false
            }
        }

        bg
    } else Color.Transparent

@Composable
private fun RowScope.TextEdit(
    value: String,
    editTitle: String,
    onSave: (String) -> Unit,
    modifier: Modifier = Modifier,
    desc: String? = null,
    placeholder: String? = null,
    keyboardType: KeyboardType = KeyboardType.Unspecified
) {
    var showDialog by rememberSaveable { mutableStateOf(false) }
    val shape = RoundedCornerShape(8.dp)
    val primary = MaterialTheme.colorScheme.primary
    var textEdit by remember(value) { mutableStateOf(value) }

    Text(
        text = value + (desc?.let { " $it" } ?: ""),
        modifier = modifier
            .clip(shape)
            .border(
                width = 1.dp,
                color = primary,
                shape = shape
            )
            .clickable {
                showDialog = true
            }
            .padding(
                vertical = 8.dp,
                horizontal = 16.dp
            ),
        color = primary
    )
    if (showDialog) Dialog(
        onDismissRequest = {
            showDialog = false
            textEdit = value
        }
    ) {
        val shape = RoundedCornerShape(16.dp)

        Column(
            modifier = Modifier
                .clip(shape)
                .border(
                    width = 2.dp,
                    color = primary,
                    shape = shape
                )
                .background(MaterialTheme.colorScheme.background)
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Text(
                text = editTitle,
                style = MaterialTheme.typography.bodyLarge.copy(
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onBackground
                )
            )
            TextField(
                value = textEdit,
                onValueChange = { textEdit = it },
                modifier = Modifier.fillMaxWidth(),
                placeholder = placeholder ?: "Ubah nilai...",
                keyboardType = keyboardType
            )
            Row(
                modifier = Modifier.align(Alignment.End),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                TextButton(
                    onClick = { showDialog = false }
                ) {
                    Text(
                        text = "Batal",
                        color = DarkGray,
                        fontWeight = FontWeight.Bold
                    )
                }
                Button(
                    text = "Simpan",
                    onClick = {
                        showDialog = false
                        onSave(textEdit)
                    },
                    enabled = textEdit != value
                )
            }
        }
    }
}