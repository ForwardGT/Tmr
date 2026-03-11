package tmr.impl.windows.main_window.work_timer

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawWithCache
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.unit.dp
import app.core.ui.components.DoubleButtons
import app.core.ui.components.TimeDisplay
import app.core.ui.resourses.TmrColors
import tmr.impl.windows.main_window.MainStore
import tmr.impl.windows.main_window.StateTimerWorkManager
import tmr.impl.windows.main_window.TmrState
import kotlin.math.PI
import kotlin.math.cos
import kotlin.math.sin

@Composable
fun WorkTimer(
    modifier: Modifier = Modifier,
    circleCount: Int = 20,
    radius: Float = 100f,
    dotSize: Float = 12f,
    store: MainStore,
    state: TmrState,
) {
    var activeIndex by remember { mutableStateOf(15) }

    if (state.currentWorkTime > 0) {
        LaunchedEffect(state.currentWorkTime) {
            activeIndex = (activeIndex + 1) % circleCount
        }
    }

    Box(
        modifier = modifier
            .drawWithCache {
                onDrawBehind {
                    val centerX = size.width / 2
                    val centerY = size.height / 2
                    val angleStep = (2 * PI / circleCount).toFloat()

                    for (i in 0 until circleCount) {
                        val angle = i * angleStep
                        val x = centerX + radius * cos(angle)
                        val y = centerY + radius * sin(angle)

                        val color = if (i == activeIndex) TmrColors.activeBar else TmrColors.inactiveComponent

                        drawCircle(
                            color = color,
                            radius = dotSize / 2,
                            center = Offset(x, y)
                        )
                    }
                }
            },
    ) {

        TimeDisplay(
            modifier = Modifier.padding(bottom = 50.dp).align(Alignment.Center),
            valueSecond = state.currentWorkTime,
        )

        Box(
            modifier = Modifier.align(Alignment.BottomCenter).padding(bottom = 52.dp),
        ) {
            DoubleButtons(
                onClickLeftButton = {
                    if (!state.timerIsRunning) {
                        store.startStopWorkTimer(StateTimerWorkManager.Start)
                    } else {
                        store.startStopWorkTimer(StateTimerWorkManager.Pause)
                    }
                },
                onClickRightButton = {
                    store.startStopWorkTimer(StateTimerWorkManager.Stop)
                    activeIndex = 15
                },
                isTimerRunning = state.timerIsRunning,
                currentTime = state.currentWorkTime,
            )
        }
    }
}
