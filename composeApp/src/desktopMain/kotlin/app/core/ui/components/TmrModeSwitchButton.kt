package app.core.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import app.core.ui.resources.TmrColors
import tmr.impl.windows.timer_window.TypeTimer

@Composable
fun TmrModeSwitchButton(
    typeTimer: TypeTimer,
    onClick: (typeTimer: TypeTimer) -> Unit,
    activeButton: TypeTimer,
) {
    Spacer(
        modifier = Modifier
            .size(17.dp)
            .clip(CircleShape)
            .background(color = if (activeButton == typeTimer) TmrColors.colorIconExit else TmrColors.inactiveComponent)
            .clickable { onClick(typeTimer) }
    )
}

@Preview
@Composable
private fun TmrModeSwitchButtonPreview() {
    var activeButton by remember { mutableStateOf(TypeTimer.WorkTimer) }

    Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
        TmrModeSwitchButton(
            typeTimer = TypeTimer.WorkTimer,
            activeButton = activeButton,
            onClick = { activeButton = it },
        )
        TmrModeSwitchButton(
            typeTimer = TypeTimer.ShutdownTimer,
            activeButton = activeButton,
            onClick = { activeButton = it },
        )
    }
}
