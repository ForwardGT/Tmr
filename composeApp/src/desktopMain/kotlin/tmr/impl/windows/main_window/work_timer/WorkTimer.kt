package org.example.project.main_window.work_timer

import androidx.compose.desktop.ui.tooling.preview.Preview
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
import kotlinx.coroutines.delay
import kotlin.math.PI
import kotlin.math.cos
import kotlin.math.sin

@Preview
@Composable
fun WorkTimer(
    modifier: Modifier = Modifier,
    circleCount: Int = 20,
    radius: Float = 100f,
    dotSize: Float = 12f,
) {
    var activeIndex by remember { mutableStateOf(15) }
    var isTimerRunning by remember { mutableStateOf(false) }
    var currentTime by remember { mutableStateOf(0) }

    LaunchedEffect(isTimerRunning) {
        if (isTimerRunning) {
            while (true) {
                currentTime += 1
                activeIndex = (activeIndex + 1) % circleCount
                delay(1000)
            }
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
            valueSecond = currentTime,
        )

        Box(
            modifier = Modifier.align(Alignment.BottomCenter).padding(bottom = 52.dp),
        ) {
            DoubleButtons(
                onClickLeftButton = { isTimerRunning = !isTimerRunning },
                onClickRightButton = { currentTime = 0; activeIndex = 15 },
                isTimerRunning = isTimerRunning,
                currentTime = currentTime,
            )
        }
    }
}
