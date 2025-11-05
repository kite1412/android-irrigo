package kite1412.irrigo.designsystem.component

import androidx.compose.animation.animateColor
import androidx.compose.animation.core.animateDp
import androidx.compose.animation.core.updateTransition
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import kite1412.irrigo.designsystem.theme.IrrigoTheme
import kite1412.irrigo.designsystem.util.IrrigoIcon
import kite1412.irrigo.util.Destination

@Composable
fun IrrigoNavBar(
    destinations: List<Destination>,
    selectedDestination: Destination,
    onDestinationSelected: (Destination) -> Unit,
    modifier: Modifier = Modifier
) {
    if (destinations.isNotEmpty()) Row(
        modifier = modifier
            .height(62.dp) // icon size + icon vert pad + vert pad
            .clip(CircleShape)
            .background(MaterialTheme.colorScheme.primary)
            .padding(8.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        destinations.forEach {
            Destination(
                destination = it,
                isSelected = selectedDestination == it,
                onClick = onDestinationSelected
            )
        }
    }
}

@Composable
private fun Destination(
    destination: Destination,
    isSelected: Boolean,
    onClick: (Destination) -> Unit,
    modifier: Modifier = Modifier
) {
    val transition = updateTransition(isSelected)
    val background by transition.animateColor {
        if (it) MaterialTheme.colorScheme.onPrimary else MaterialTheme.colorScheme.primary
    }
    val padding by transition.animateDp {
        if (it) 8.dp else 0.dp
    }

    Icon(
        painter = painterResource(destination.iconId),
        contentDescription = destination.name,
        modifier = modifier
            .background(
                color = background,
                shape = CircleShape
            )
            .clickable(
                interactionSource = null,
                indication = null
            ) { onClick(destination) }
            .padding(padding)
            .size(32.dp),
        tint = Color.Black
    )
}

private object MockDestination

@Preview
@Composable
private fun IrrigoNavBarPreview() {
    val destinations = listOf(
        Destination(
            route = MockDestination::class,
            iconId = IrrigoIcon.chart,
            name = "Dashboard"
        ),
        Destination(
            route = MockDestination::class,
            iconId = IrrigoIcon.clipboard,
            name = "Log"
        ),
        Destination(
            route = MockDestination::class,
            iconId = IrrigoIcon.sliders,
            name = "Setting"
        )
    )
    var selectedDestination by remember {
        mutableStateOf(destinations.first())
    }

    IrrigoTheme {
        IrrigoNavBar(
            destinations = destinations,
            selectedDestination = selectedDestination,
            onDestinationSelected = {
                selectedDestination = it
            }
        )
    }
}