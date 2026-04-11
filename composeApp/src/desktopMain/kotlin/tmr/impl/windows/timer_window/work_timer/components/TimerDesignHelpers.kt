package tmr.impl.windows.timer_window.work_timer.components

import androidx.compose.animation.core.Easing
import androidx.compose.animation.core.InfiniteTransition
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import app.core.ui.components.TimeDisplay
import tmr.impl.windows.timer_window.TmrState

@Composable
internal fun animatedFloatOrDefault(
    transition: InfiniteTransition?,
    initialValue: Float,
    targetValue: Float,
    durationMillis: Int,
    easing: Easing,
    repeatMode: RepeatMode,
    label: String,
    defaultValue: Float,
): Float {
    if (transition == null) return defaultValue
    val value by transition.animateFloat(
        initialValue = initialValue,
        targetValue = targetValue,
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis, easing = easing),
            repeatMode = repeatMode,
        ),
        label = label,
    )
    return value
}

@Composable
internal fun WorkCenterContent(
    state: TmrState,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier.padding(bottom = 50.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        TimeDisplay(valueSecond = state.currentWorkTime)
    }
}
