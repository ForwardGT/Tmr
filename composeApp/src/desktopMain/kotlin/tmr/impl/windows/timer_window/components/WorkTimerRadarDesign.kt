package tmr.impl.windows.timer_window.components

import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawWithCache
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.unit.dp
import app.core.ui.resources.TmrColors
import tmr.impl.windows.timer_window.StateTimerManager
import tmr.impl.windows.timer_window.TimerStore
import tmr.impl.windows.timer_window.TmrState

@Composable
internal fun WorkTimerRadarDesign(
    modifier: Modifier,
    state: TmrState,
    store: TimerStore,
) {
    val isAnimating = state.stateTimerManager == StateTimerManager.Start
    val transition = if (isAnimating) rememberInfiniteTransition(label = "work-radar") else null
    val sweep = animatedFloatOrDefault(
        transition = transition,
        initialValue = -90f,
        targetValue = 270f,
        durationMillis = 2800,
        easing = LinearEasing,
        repeatMode = RepeatMode.Restart,
        label = "sweep",
        defaultValue = -90f,
    )
    val breathing = animatedFloatOrDefault(
        transition = transition,
        initialValue = 0.25f,
        targetValue = 0.65f,
        durationMillis = 1800,
        easing = LinearEasing,
        repeatMode = RepeatMode.Reverse,
        label = "breathing",
        defaultValue = 0.25f,
    )

    Box(
        modifier = modifier.drawWithCache {
            val center = Offset(size.width / 2f, size.height / 2f)
            val baseRadius = size.minDimension * 0.50f
            val sweepTint = TmrColors.activeBar.copy(alpha = 0.1f + 0.2f * breathing)
            val blips = generateRadarBlips(seed = 1337, count = 5)

            onDrawBehind {
                drawRadarRingsAndCrosshair(center = center, radius = baseRadius)

                if (isAnimating) {
                    drawArc(
                        color = sweepTint,
                        startAngle = sweep,
                        sweepAngle = 42f,
                        useCenter = true,
                    )
                }

                if (isAnimating) {
                    drawRadarBlips(
                        center = center,
                        radius = baseRadius,
                        blips = blips,
                        sweepAngleDegrees = sweep,
                        pulse = breathing,
                    )
                }

                drawCircle(
                    color = TmrColors.activeBar.copy(alpha = 0.09f + 0.135f * breathing),
                    center = center,
                    radius = (3.4f + breathing * 1.25f).dp.toPx(),
                )
            }
        }
    ) {
        WorkCenterContent(state = state, modifier = Modifier.align(Alignment.Center))

        WorkTimerButtons(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(bottom = 24.dp),
            state = state,
            store = store,
        )
    }
}
