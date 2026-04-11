package tmr.impl.windows.timer_window.work_timer.components

import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawWithCache
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.unit.dp
import app.core.ui.resources.TmrColors
import tmr.impl.windows.timer_window.StateTimerManager
import tmr.impl.windows.timer_window.TimerStore
import tmr.impl.windows.timer_window.TmrState
import tmr.impl.windows.timer_window.work_timer.WorkTimerButtons
import kotlin.math.PI
import kotlin.math.abs
import kotlin.math.cos
import kotlin.math.sin

@Composable
internal fun WorkTimerFocusDesign(
    modifier: Modifier,
    state: TmrState,
    store: TimerStore,
) {
    val isAnimating = state.stateTimerManager == StateTimerManager.Start
    val progress = remember(state.currentWorkTime) {
        ((state.currentWorkTime % 7200) / 7200f).coerceIn(0f, 1f)
    }

    val transition = if (isAnimating) rememberInfiniteTransition(label = "work-focus-iris") else null
    val orbitA = animatedFloatOrDefault(
        transition = transition,
        initialValue = 0f,
        targetValue = 360f,
        durationMillis = 3400,
        easing = FastOutSlowInEasing,
        repeatMode = RepeatMode.Restart,
        label = "orbitA",
        defaultValue = 0f,
    )
    val orbitB = animatedFloatOrDefault(
        transition = transition,
        initialValue = 360f,
        targetValue = 0f,
        durationMillis = 2900,
        easing = FastOutSlowInEasing,
        repeatMode = RepeatMode.Restart,
        label = "orbitB",
        defaultValue = 360f,
    )
    val pulse = animatedFloatOrDefault(
        transition = transition,
        initialValue = 0.22f,
        targetValue = 0.78f,
        durationMillis = 1600,
        easing = LinearEasing,
        repeatMode = RepeatMode.Reverse,
        label = "pulse",
        defaultValue = 0.22f,
    )
    val pointPhase = animatedFloatOrDefault(
        transition = transition,
        initialValue = 0f,
        targetValue = 28f,
        durationMillis = 1500,
        easing = LinearEasing,
        repeatMode = RepeatMode.Restart,
        label = "pointPhase",
        defaultValue = 0f,
    )

    Box(
        modifier = modifier.drawWithCache {
            val center = Offset(size.width / 2f, size.height / 2f)
            val rPointOuter = size.minDimension * 0.50f
            val rMid = size.minDimension * 0.34f
            val midRingStroke = 1.2.dp.toPx() * (0.9f + 0.2f * pulse)
            val orbitInsetA = 8.dp.toPx()
            val orbitInsetB = 13.dp.toPx()

            onDrawBehind {
                val dotCount = 28
                repeat(dotCount) { i ->
                    val angle = ((i * (360f / dotCount)) - 90f) * (PI / 180f)
                    val dot = Offset(
                        x = center.x + rPointOuter * cos(angle).toFloat(),
                        y = center.y + rPointOuter * sin(angle).toFloat(),
                    )
                    val dotAlpha = if (isAnimating) {
                        val rawDistance = abs(i - pointPhase)
                        val distance = minOf(rawDistance, dotCount - rawDistance)
                        val lock = 1f - (distance / 3.1f).coerceIn(0f, 1f)
                        0.12f + lock * (0.34f + 0.28f * pulse)
                    } else if (i < (progress * dotCount).toInt()) {
                        0.18f + 0.24f * pulse
                    } else {
                        0.12f
                    }
                    drawCircle(
                        color = if (isAnimating || i < (progress * dotCount).toInt()) {
                            TmrColors.activeSwitch.copy(alpha = dotAlpha)
                        } else {
                            TmrColors.inactiveBar.copy(alpha = dotAlpha)
                        },
                        radius = 1.7.dp.toPx(),
                        center = dot,
                    )
                }

                drawCircle(
                    color = TmrColors.inactiveBar.copy(alpha = 0.12f + 0.1f * pulse),
                    radius = rMid,
                    center = center,
                    style = Stroke(width = midRingStroke),
                )

                repeat(12) { i ->
                    val angle = ((i * 30f) - 90f) * (PI / 180f)
                    val inner = Offset(
                        x = center.x + (rMid - 6.dp.toPx()) * cos(angle).toFloat(),
                        y = center.y + (rMid - 6.dp.toPx()) * sin(angle).toFloat(),
                    )
                    val outer = Offset(
                        x = center.x + (rPointOuter - 4.dp.toPx()) * cos(angle).toFloat(),
                        y = center.y + (rPointOuter - 4.dp.toPx()) * sin(angle).toFloat(),
                    )
                    drawLine(
                        color = if (i < (progress * 12).toInt()) {
                            TmrColors.activeSwitch.copy(alpha = 0.24f + 0.36f * pulse)
                        } else {
                            TmrColors.focusInactiveTick
                        },
                        start = inner,
                        end = outer,
                        strokeWidth = 1.4.dp.toPx(),
                    )
                }

                drawArc(
                    color = TmrColors.activeBar.copy(alpha = 0.2f + 0.34f * pulse),
                    startAngle = orbitA,
                    sweepAngle = 62f + 20f * pulse,
                    useCenter = false,
                    topLeft = Offset(orbitInsetA, orbitInsetA),
                    size = size.copy(width = size.width - orbitInsetA * 2, height = size.height - orbitInsetA * 2),
                    style = Stroke(4.dp.toPx(), cap = StrokeCap.Round),
                )

                drawArc(
                    color = TmrColors.activeSwitch.copy(alpha = 0.22f + 0.32f * pulse),
                    startAngle = orbitB,
                    sweepAngle = 38f + 14f * pulse,
                    useCenter = false,
                    topLeft = Offset(orbitInsetB, orbitInsetB),
                    size = size.copy(width = size.width - orbitInsetB * 2, height = size.height - orbitInsetB * 2),
                    style = Stroke(3.dp.toPx(), cap = StrokeCap.Round),
                )
            }
        }
    ) {
        WorkCenterContent(
            modifier = Modifier.align(Alignment.Center),
            state = state,
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
