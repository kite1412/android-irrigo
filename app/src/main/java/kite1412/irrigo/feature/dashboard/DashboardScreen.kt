package kite1412.irrigo.feature.dashboard

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.animateColor
import androidx.compose.animation.core.animateDp
import androidx.compose.animation.core.updateTransition
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.LocalWindowInfo
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.max
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import kite1412.irrigo.designsystem.util.IrrigoIcon
import kite1412.irrigo.model.Device

@Composable
fun DashboardScreen(
    modifier: Modifier = Modifier,
    viewModel: DashboardViewModel = hiltViewModel()
) {
    val device = viewModel.device

    AnimatedContent(
        targetState = device,
        modifier = modifier
    ) {
        if (it != null) LazyColumn(
            modifier = modifier,
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            item {
                DeviceSelect(
                    selectedDevice = it,
                    devices = viewModel.devices,
                    onDeviceChange = viewModel::onDeviceChange
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