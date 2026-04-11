package tmr.impl.windows.timer_window.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import app.application.configurations.TimerDesign
import app.core.ui.components.DoubleButtons
import app.core.ui.components.TimeDisplay
import app.core.ui.components.TmrButton
import app.core.ui.components.TmrTextField
import app.core.ui.resources.TmrColors
import app.core.ui.resources.TmrColors.colorIconExit
import tmr.composeapp.generated.resources.Res
import tmr.composeapp.generated.resources.gear
import tmr.impl.windows.timer_window.TimerStore
import tmr.impl.windows.timer_window.TmrState

@Composable
fun ShutdownTimerView(
    modifier: Modifier = Modifier,
    store: TimerStore,
    state: TmrState,
    timerDesign: TimerDesign,
    strokeWidth: Dp = 5.dp,
) {
    val progress = remember(state.shutdownCurrentTime, state.shutdownTotalTime) {
        if (state.shutdownTotalTime == 0) 0f
        else state.shutdownCurrentTime / state.shutdownTotalTime.toFloat()
    }

    when (timerDesign) {
        TimerDesign.Minimal -> ShutdownTimerMinimalDesign(
            modifier = modifier,
            store = store,
            state = state,
            progress = progress,
            strokeWidth = strokeWidth,
        )

        TimerDesign.Focus -> ShutdownTimerFocusDesign(
            modifier = modifier,
            store = store,
            state = state,
            progress = progress,
        )

        TimerDesign.Radar -> ShutdownTimerRadarDesign(
            modifier = modifier,
            store = store,
            state = state,
            progress = progress,
        )
    }
}

@Composable
internal fun ShutdownButtons(
    state: TmrState,
    store: TimerStore,
    modifier: Modifier,
) {
    DoubleButtons(
        modifier = modifier,
        isTimerRunning = state.isShutdownTimerRunning,
        currentTime = state.shutdownCurrentTime,
        onClickRightButton = store::resetShutdownTimer,
        onClickLeftButton = store::startPauseShutdownTimer,
    )
}

@Composable
internal fun ShutdownCenterContent(
    state: TmrState,
    store: TimerStore,
    modifier: Modifier = Modifier,
) {
    if (state.isShutdownEditMode) {
        Box(modifier = modifier.padding(bottom = 50.dp)) {
            TmrTextField(
                submit = store::updateShutdownMinutes,
                autoFocus = true,
            )
        }
    } else {
        Row(
            horizontalArrangement = Arrangement.Center,
            modifier = modifier.padding(start = 14.dp),
        ) {
            TimeDisplay(
                modifier = Modifier.padding(bottom = 50.dp),
                valueSecond = state.shutdownCurrentTime,
            )

            TmrButton(
                modifier = Modifier.size(14.dp),
                text = "",
                icon = Res.drawable.gear,
                onClick = store::toggleShutdownEditMode,
                colorGradientBackground = TmrColors.offButtonGradient,
                colorIcon = colorIconExit,
                isExitButton = true,
            )
        }
    }
}
