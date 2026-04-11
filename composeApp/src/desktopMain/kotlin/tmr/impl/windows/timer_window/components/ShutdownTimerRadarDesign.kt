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
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.unit.dp
import app.core.ui.resources.TmrColors
import tmr.impl.windows.timer_window.TimerStore
import tmr.impl.windows.timer_window.TmrState

@Composable
internal fun ShutdownTimerRadarDesign(
    modifier: Modifier,
    store: TimerStore,
    state: TmrState,
    progress: Float,
) {
    val isAnimating = state.isShutdownTimerRunning
    val transition = if (isAnimating) rememberInfiniteTransition(label = "shutdown-radar") else null
    val radarRotation = animatedFloatOrDefault(
        transition = transition,
        initialValue = -90f,
        targetValue = 270f,
        durationMillis = 3200,
        easing = LinearEasing,
        repeatMode = RepeatMode.Restart,
        label = "radarRotation",
        defaultValue = -90f,
    )
    val pulse = animatedFloatOrDefault(
        transition = transition,
        initialValue = 0.25f,
        targetValue = 0.65f,
        durationMillis = 1800,
        easing = LinearEasing,
        repeatMode = RepeatMode.Reverse,
        label = "pulse",
        defaultValue = 0.25f,
    )
    val waveProgress = animatedFloatOrDefault(
        transition = transition,
        initialValue = 0f,
        targetValue = 1f,
        durationMillis = 1900,
        easing = LinearEasing,
        repeatMode = RepeatMode.Restart,
        label = "waveProgress",
        defaultValue = 0f,
    )

    Box(
        modifier = modifier.drawWithCache {
            val center = Offset(size.width / 2f, size.height / 2f)
            val radius = size.minDimension * 0.50f
            val sweepTint = TmrColors.activeBar.copy(alpha = 0.1f + pulse * 0.2f + 0.05f * progress.coerceIn(0f, 1f))
            val blips = generateRadarBlips(seed = 1337, count = 5)

            onDrawBehind {
                drawRadarRingsAndCrosshair(center = center, radius = radius)

                if (isAnimating) {
                    drawArc(
                        color = sweepTint,
                        startAngle = radarRotation,
                        sweepAngle = 42f,
                        useCenter = true,
                    )
                }

                drawCircle(
                    color = TmrColors.activeBar.copy(alpha = 0.02f + 0.22f * waveProgress),
                    center = center,
                    radius = (radius * waveProgress.coerceAtLeast(0.03f)),
                    style = Stroke(width = 2.dp.toPx()),
                )

                if (isAnimating) {
                    drawRadarBlips(
                        center = center,
                        radius = radius,
                        blips = blips,
                        sweepAngleDegrees = radarRotation,
                        pulse = pulse,
                    )
                }

                drawCircle(
                    color = TmrColors.activeBar.copy(alpha = 0.09f + 0.135f * pulse),
                    center = center,
                    radius = (3.4f + pulse * 1.25f).dp.toPx(),
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
