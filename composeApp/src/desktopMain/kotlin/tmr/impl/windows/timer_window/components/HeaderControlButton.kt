package tmr.impl.windows.timer_window.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import app.core.ui.components.TmrButton
import app.core.ui.components.TmrModeSwitchButton
import app.core.ui.components.TmrSpacer
import app.core.ui.resources.TmrColors
import tmr.composeapp.generated.resources.Res
import tmr.composeapp.generated.resources.exit
import tmr.composeapp.generated.resources.gear
import tmr.impl.windows.timer_window.TmrState
import tmr.impl.windows.timer_window.TypeTimer

@Composable
internal fun HeaderControlButton(
    modifier: Modifier = Modifier,
    state: TmrState,
    onClickSwitch: (TypeTimer) -> Unit,
    onClickCloseApp: () -> Unit,
    onClickOptions: () -> Unit,
) {
    Row(
        modifier = modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Row {
            TmrModeSwitchButton(
                onClick = { onClickSwitch(it) },
                typeTimer = TypeTimer.WorkTimer,
                activeButton = state.typeTimer,
            )
            TmrSpacer(width = 4.dp)
            TmrModeSwitchButton(
                onClick = { onClickSwitch(it) },
                typeTimer = TypeTimer.ShutdownTimer,
                activeButton = state.typeTimer,
            )
        }
        Row(
            verticalAlignment = Alignment.CenterVertically,
        ) {
            TmrButton(
                modifier = Modifier.size(17.dp),
                text = "",
                icon = Res.drawable.gear,
                onClick = { onClickOptions() },
                colorGradientBackground = TmrColors.offButtonGradient,
                colorIcon = TmrColors.colorIconExit,
                isExitButton = true
            )
            TmrSpacer(width = 4.dp)
            TmrButton(
                text = "",
                onClick = onClickCloseApp,
                isExitButton = true,
                icon = Res.drawable.exit,
                colorGradientBackground = TmrColors.offButtonGradient,
                colorIcon = TmrColors.colorIconExit,
            )
        }
    }
}