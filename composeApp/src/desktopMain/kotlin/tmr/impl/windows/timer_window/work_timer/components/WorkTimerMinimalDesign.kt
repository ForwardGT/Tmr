package tmr.impl.windows.timer_window.work_timer.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawWithCache
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.unit.dp
import app.core.ui.resources.TmrColors
import tmr.impl.windows.timer_window.TimerStore
import tmr.impl.windows.timer_window.TmrState
import tmr.impl.windows.timer_window.work_timer.WorkTimerButtons

@Composable
internal fun WorkTimerMinimalDesign(
    modifier: Modifier,
    state: TmrState,
    store: TimerStore,
    alpha: Float,
) {
    Box(
        modifier = modifier
            .drawWithCache {
                val strokeWidth = 6.dp.toPx()

                onDrawBehind {
                    drawArc(
                        color = TmrColors.activeBar.copy(alpha = alpha),
                        startAngle = 0f,
                        sweepAngle = 360f,
                        useCenter = false,
                        style = Stroke(width = strokeWidth),
                    )
                }
            }
    ) {
        WorkCenterContent(
            state = state,
            modifier = Modifier.align(Alignment.Center),
        )

        WorkTimerButtons(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(bottom = 24.dp),
            state = state,
            store = store,
        )
    }
}
