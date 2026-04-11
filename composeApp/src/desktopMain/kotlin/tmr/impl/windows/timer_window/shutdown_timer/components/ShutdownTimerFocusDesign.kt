package tmr.impl.windows.timer_window.shutdown_timer.components

import androidx.compose.animation.core.FastOutSlowInEasing
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
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.unit.dp
import app.core.ui.resources.TmrColors
import tmr.impl.windows.timer_window.TimerStore
import tmr.impl.windows.timer_window.TmrState
import tmr.impl.windows.timer_window.work_timer.components.animatedFloatOrDefault
import tmr.impl.windows.timer_window.shutdown_timer.ShutdownButtons
import tmr.impl.windows.timer_window.shutdown_timer.ShutdownCenterContent
import kotlin.math.PI
import kotlin.math.abs
import kotlin.math.cos
import kotlin.math.sin

@Composable
internal fun ShutdownTimerFocusDesign(
    modifier: Modifier,
    store: TimerStore,
    state: TmrState,
    progress: Float,
) {
    val isAnimating = state.isShutdownTimerRunning
    val safeProgress = progress.coerceIn(0f, 1f)

    val transition = if (isAnimating) rememberInfiniteTransition(label = "shutdown-focus-core") else null
    val ringA = animatedFloatOrDefault(
        transition = transition,
        initialValue = 0f,
        targetValue = 360f,
        durationMillis = 3250,
        easing = FastOutSlowInEasing,
        repeatMode = RepeatMode.Restart,
        label = "ringA",
        defaultValue = 0f,
    )

    val ringB = animatedFloatOrDefault(
        transition = transition,
        initialValue = 360f,
        targetValue = 0f,
        durationMillis = 2730,
        easing = FastOutSlowInEasing,
        repeatMode = RepeatMode.Restart,
        label = "ringB",
        defaultValue = 360f,
    )

    val pulse = animatedFloatOrDefault(
        transition = transition,
        initialValue = 0.22f,
        targetValue = 0.82f,
        durationMillis = 1700,
        easing = LinearEasing,
        repeatMode = RepeatMode.Reverse,
        label = "pulse",
        defaultValue = 0.22f,
    )

    val pointPhase = animatedFloatOrDefault(
        transition = transition,
        initialValue = 0f,
        targetValue = 24f,
        durationMillis = 2200,
        easing = LinearEasing,
        repeatMode = RepeatMode.Restart,
        label = "pointPhase",
        defaultValue = 0f,
    )

    Box(
        modifier = modifier.drawWithCache {
            val center = Offset(size.width / 2f, size.height / 2f)
            val radiusOuter = size.minDimension * 0.43f
            val strokeOuter = 6.5.dp.toPx()

            onDrawBehind {
                drawArc(
                    color = TmrColors.activeBar.copy(alpha = 0.3f + 0.34f * pulse),
                    startAngle = -90f,
                    sweepAngle = 360f * safeProgress,
                    useCenter = false,
                    style = Stroke(strokeOuter, cap = StrokeCap.Round),
                )

                repeat(24) { i ->
                    val angle = ((i * 15f) - 90f) * (PI / 180f)
                    val p = Offset(
                        x = center.x + radiusOuter * cos(angle).toFloat(),
                        y = center.y + radiusOuter * sin(angle).toFloat(),
                    )
                    val pointAlpha = if (isAnimating) {
                        val rawDistance = abs(i - pointPhase)
                        val distance = minOf(rawDistance, 24f - rawDistance)
                        val lock = 1f - (distance / 3.2f).coerceIn(0f, 1f)
                        0.14f + lock * (0.3f + 0.28f * pulse)
                    } else if (i < (safeProgress * 24).toInt()) {
                        0.22f + 0.5f * pulse
                    } else {
                        0.16f
                    }
                    drawCircle(
                        color = if (isAnimating || i < (safeProgress * 24).toInt()) {
                            TmrColors.activeSwitch.copy(alpha = pointAlpha)
                        } else {
                            TmrColors.inactiveBar.copy(alpha = pointAlpha)
                        },
                        center = p,
                        radius = 1.7.dp.toPx(),
                    )
                }

                drawArc(
                    color = TmrColors.activeBar.copy(alpha = 0.16f + 0.28f * pulse),
                    startAngle = ringA,
                    sweepAngle = 64f,
                    useCenter = false,
                    style = Stroke(2.6.dp.toPx(), cap = StrokeCap.Round),
                )

                drawArc(
                    color = TmrColors.activeSwitch.copy(alpha = 0.18f + 0.26f * pulse),
                    startAngle = ringB,
                    sweepAngle = 36f,
                    useCenter = false,
                    style = Stroke(2.dp.toPx(), cap = StrokeCap.Round),
                )

            }
        },
        contentAlignment = Alignment.Center,
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
