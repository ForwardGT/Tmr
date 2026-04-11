package tmr.impl.windows.timer_window.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawWithCache
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import app.core.ui.resources.TmrColors
import tmr.impl.windows.timer_window.TimerStore
import tmr.impl.windows.timer_window.TmrState

@Composable
internal fun ShutdownTimerMinimalDesign(
    modifier: Modifier,
    store: TimerStore,
    state: TmrState,
    progress: Float,
    strokeWidth: Dp,
) {
    Box(
        modifier = modifier.drawWithCache {
            onDrawBehind {
                drawArc(
                    color = TmrColors.inactiveBar,
                    startAngle = -215f,
                    sweepAngle = 250f,
                    useCenter = false,
                    style = Stroke(strokeWidth.toPx(), cap = StrokeCap.Round),
                )
                drawArc(
                    color = TmrColors.activeBar,
                    startAngle = -215f,
                    sweepAngle = 250f * progress,
                    useCenter = false,
                    style = Stroke(strokeWidth.toPx(), cap = StrokeCap.Round),
                )
            }
        },
    ) {
        ShutdownCenterContent(
            state = state,
            store = store,
            modifier = Modifier.align(Alignment.Center),
        )
        ShutdownButtons(
            state = state,
            store = store,
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(bottom = 24.dp),
        )
    }
}
