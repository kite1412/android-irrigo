package kite1412.irrigo.feature.devicesettings

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import kite1412.irrigo.designsystem.component.Toggle
import kite1412.irrigo.feature.devicesettings.util.Setting

@Composable
fun DeviceSettingsScreen(
    modifier: Modifier = Modifier,
    viewModel: DeviceSettingsViewModel = hiltViewModel()
) {
    LazyColumn(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        item {
            WateringSection(
                isAutomated = viewModel.isAutomated,
                onAutomatedChange = viewModel::updateAutomatedSetting,
                wateringReminder = viewModel.wateringReminder,
                onWateringReminderChange = viewModel::updateWateringReminder
            )
        }
        item {
            WaterCapacitySection(
                waterCapacityReminder = viewModel.waterCapacityReminder,
                onWaterCapacityReminderChange = viewModel::updateWaterCapacityReminder
            )
        }
    }
}

@Composable
private fun WateringSection(
    isAutomated: Boolean,
    onAutomatedChange: (Boolean) -> Unit,
    wateringReminder: Boolean,
    onWateringReminderChange: (Boolean) -> Unit,
    modifier: Modifier = Modifier
) = Section(
    name = "Penyiraman",
    modifier = modifier
) {
    Setting(Setting.WATERING_AUTOMATED) {
        Toggle(
            checked = isAutomated,
            onCheckedChange = onAutomatedChange
        )
    }
    Setting(Setting.WATERING_MIN_HUMIDITY) {

    }
    Setting(Setting.WATERING_REMINDER) {
        Toggle(
            checked = wateringReminder,
            onCheckedChange = onWateringReminderChange
        )
    }
    Setting(Setting.WATERING_DURATION) {

    }
}

@Composable
private fun WaterCapacitySection(
    waterCapacityReminder: Boolean,
    onWaterCapacityReminderChange: (Boolean) -> Unit,
    modifier: Modifier = Modifier
) = Section(
    name = "Kapasitas Air",
    modifier = modifier
) {
    Setting(
        setting = Setting.WATER_CAPACITY_REMINDER,
        subSettings = {
            SubSetting(Setting.WATER_CAPACITY_MIN_CAPACITY) {

            }
        }
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
    action: @Composable RowScope.() -> Unit
) {
    Row(
        modifier = modifier.fillMaxWidth(),
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
private fun SubSetting(
    setting: Setting,
    modifier: Modifier = Modifier,
    action: @Composable RowScope.() -> Unit
) {
    Row(
        modifier = modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) { 
        Text(setting.displayName)
        action(this)
    }
}