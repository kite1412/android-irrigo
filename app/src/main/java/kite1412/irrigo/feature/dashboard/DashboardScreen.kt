package kite1412.irrigo.feature.dashboard

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalWindowInfo
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Popup
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import kite1412.irrigo.designsystem.util.IrrigoIcon
import kite1412.irrigo.model.Device

@Composable
fun DashboardScreen(
    modifier: Modifier = Modifier,
    viewModel: DashboardViewModel = hiltViewModel()
) {
    LazyColumn(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {

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
    val windowInfo = LocalWindowInfo.current

    Row(
        modifier = modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = selectedDevice.name,
            modifier = Modifier
                .clickable {
                    expanded = !expanded
                },
            fontStyle = FontStyle.Italic
        )
        Icon(
            painter = painterResource(IrrigoIcon.chevronDown),
            contentDescription = null
        )
    }
    Popup(
        onDismissRequest = {
            expanded = false
        }
    ) {
        LazyColumn(
            modifier = Modifier.heightIn(
                max = (windowInfo.containerSize.height / 2).dp
            )
        ) {
            items(devices) {
                Text(
                    text = it.name,
                    modifier = Modifier.clickable {
                        onDeviceChange(it)
                    }
                )
            }
        }
    }
}