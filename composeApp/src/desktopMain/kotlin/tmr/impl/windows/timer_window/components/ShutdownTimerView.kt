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
import androidx.compose.ui.draw.drawWithCache
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
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
    strokeWidth: Dp = 5.dp,
) {
    val progress = remember(state.shutdownCurrentTime, state.shutdownTotalTime) {
        if (state.shutdownTotalTime == 0) 0f
        else state.shutdownCurrentTime / state.shutdownTotalTime.toFloat()
    }

    Box(
        modifier = modifier,
        contentAlignment = Alignment.Center,
    ) {
        Box(
            modifier = modifier
                .drawWithCache {
                    onDrawBehind {

                        val size = size.copy(width = size.width, height = size.height)

                        drawArc(
                            color = TmrColors.inactiveBar,
                            startAngle = -215f,
                            sweepAngle = 250f,
                            useCenter = false,
                            size = size,
                            style = Stroke(strokeWidth.toPx(), cap = StrokeCap.Round)
                        )
                        drawArc(
                            color = TmrColors.activeBar,
                            startAngle = -215f,
                            sweepAngle = 250f * progress,
                            useCenter = false,
                            size = size,
                            style = Stroke(strokeWidth.toPx(), cap = StrokeCap.Round)
                        )
                    }
                }
        ) {
            if (state.isShutdownEditMode) {
                Box(
                    modifier = Modifier
                        .padding(bottom = 50.dp)
                        .align(Alignment.Center),
                ) {
                    TmrTextField(submit = store::updateShutdownMinutes)
                }

            } else {
                Row(
                    horizontalArrangement = Arrangement.Center,
                    modifier = Modifier
                        .padding(start = 14.dp)
                        .align(Alignment.Center),
                ) {
                    TimeDisplay(
                        modifier = Modifier.padding(bottom = 50.dp),
                        valueSecond = state.shutdownCurrentTime
                    )

                    TmrButton(
                        modifier = Modifier.size(14.dp),
                        text = "",
                        icon = Res.drawable.gear,
                        onClick = store::toggleShutdownEditMode,
                        colorGradientBackground = TmrColors.offButtonGradient,
                        colorIcon = colorIconExit,
                        isExitButton = true
                    )
                }

            }

            DoubleButtons(
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .padding(bottom = 24.dp),
                isTimerRunning = state.isShutdownTimerRunning,
                currentTime = state.shutdownCurrentTime,
                onClickRightButton = store::resetShutdownTimer,
                onClickLeftButton = store::startPauseShutdownTimer,
            )
        }
    }
}
