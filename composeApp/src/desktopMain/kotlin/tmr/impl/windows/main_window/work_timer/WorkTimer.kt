package tmr.impl.windows.main_window.work_timer

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawWithCache
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.unit.dp
import app.core.ui.components.DoubleButtons
import app.core.ui.components.TimeDisplay
import app.core.ui.resourses.TmrColors
import tmr.impl.windows.main_window.TimerStore
import tmr.impl.windows.main_window.StateTimerManager
import tmr.impl.windows.main_window.TmrState

@Composable
fun WorkTimer(
    modifier: Modifier = Modifier,
    store: TimerStore,
    state: TmrState,
) {

    val alpha = remember { Animatable(0.1f) }

    LaunchedEffect(state.stateTimerManager) {
        when (state.stateTimerManager) {

            StateTimerManager.Start -> {
                alpha.animateTo(
                    targetValue = 1f,
                    animationSpec = tween(500),
                )
            }

            StateTimerManager.Pause -> {
                while (true) {
                    alpha.animateTo(
                        targetValue = 0.1f,
                        animationSpec = tween(1200),
                    )
                    alpha.animateTo(
                        targetValue = 1f,
                        animationSpec = tween(1200),
                    )
                }
            }

            StateTimerManager.Stop -> {
                alpha.animateTo(
                    targetValue = 0.1f,
                    animationSpec = tween(500)
                )
            }
        }
    }

    Box(
        modifier = modifier.padding(16.dp)
            .drawWithCache {
                val strokeWidth = 6.dp.toPx()

                onDrawBehind {
                    drawArc(
                        color = TmrColors.activeBar.copy(alpha = alpha.value),
                        startAngle = 0f,
                        sweepAngle = 360f,
                        useCenter = false,
                        style = Stroke(width = strokeWidth)
                    )
                }
            }
    ) {

        TimeDisplay(
            modifier = Modifier
                .padding(bottom = 50.dp)
                .align(Alignment.Center),
            valueSecond = state.currentWorkTime,
        )

        DoubleButtons(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(bottom = 30.dp),
            onClickLeftButton = {
                if (state.stateTimerManager != StateTimerManager.Start) {
                    store.startStopWorkTimer(StateTimerManager.Start)
                } else {
                    store.startStopWorkTimer(StateTimerManager.Pause)
                }
            },
            onClickRightButton = {
                store.startStopWorkTimer(StateTimerManager.Stop)
            },
            isTimerRunning = state.stateTimerManager == StateTimerManager.Start,
            currentTime = state.currentWorkTime,
        )

    }
}
