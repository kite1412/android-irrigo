package kite1412.irrigo.feature.logs

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
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
import kite1412.irrigo.designsystem.theme.LightPastelBlue
import kite1412.irrigo.designsystem.theme.PastelBlue
import kite1412.irrigo.designsystem.util.IrrigoIcon

@Composable
fun LogsScreen(modifier: Modifier = Modifier) {
    Column(
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
                name = "Kelembaban Tanah",
                iconId = IrrigoIcon.plantWater,
                background = MaterialTheme.colorScheme.primary
            )
            LogsGroup(
                name = "Penyiraman",
                iconId = IrrigoIcon.waterSpray,
                background = LightPastelBlue
            )
            LogsGroup(
                name = "Kapasitas Air",
                iconId = IrrigoIcon.waterDrop,
                background = PastelBlue
            )
        }
    }
}

@Composable
private fun LogsGroup(
    name: String,
    iconId: Int,
    background: Color,
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
                text = name,
                style = textStyle
            )
            Icon(
                painter = painterResource(iconId),
                contentDescription = name,
                modifier = Modifier.size((textStyle.fontSize.value * 2f).dp)
            )
        }
    }
}