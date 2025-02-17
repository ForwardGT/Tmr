package org.example.project.main_window.shutdown_timer

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.delay
import org.example.project.core.ui.resourses.TmrColors
import org.example.project.main_window.shutdown_timer.components.TimeDisplay
import org.example.project.main_window.work_timer.components.DoubleButtons

@Composable
fun ShutdownTimer(
    modifier: Modifier = Modifier,
    totalTime: Long,
    initialValue: Float = 1f,
    strokeWidth: Dp = 5.dp,
) {
    var size by remember { mutableStateOf(IntSize.Zero) }
    var value by remember { mutableStateOf(initialValue) }
    var currentTime by remember { mutableStateOf(totalTime) }
    var isTimerRunning by remember { mutableStateOf(false) }

    LaunchedEffect(key1 = currentTime, key2 = isTimerRunning) {
        if (currentTime > 0 && isTimerRunning) {
            delay(100L)
            currentTime -= 100L
            value = currentTime / totalTime.toFloat()
        }
    }

    if (currentTime == 0L) {
        ProcessBuilder("cmd", "/c", "shutdown -s -t 0")
            .start()
    }

    Spacer(modifier = Modifier.padding(top = 16.dp))
    Box(
        modifier = modifier,
        contentAlignment = Alignment.Center,
    ) {
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier
                .size(200.dp)
                .onSizeChanged { size = it }
        ) {
            Canvas(modifier = Modifier.size(200.dp)) {
                drawArc(
                    color = TmrColors.inactiveComponent,
                    startAngle = -215f,
                    sweepAngle = 250f,
                    useCenter = false,
                    size = Size(size.width.toFloat(), size.height.toFloat()),
                    style = Stroke(strokeWidth.toPx(), cap = StrokeCap.Round)
                )
                drawArc(
                    color = TmrColors.activeBar,
                    startAngle = -215f,
                    sweepAngle = 250f * value,
                    useCenter = false,
                    size = Size(size.width.toFloat(), size.height.toFloat()),
                    style = Stroke(strokeWidth.toPx(), cap = StrokeCap.Round)
                )
            }

        }
        Column(
            modifier = Modifier
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Spacer(modifier = Modifier.fillMaxHeight(.35f))
            TimeDisplay(valueSecond = (currentTime / 1000L).toInt())

            Spacer(modifier = Modifier.padding(top = 26.dp))
            DoubleButtons(

                isTimerRunning = isTimerRunning,
                currentTime = currentTime.toInt(),
                onClickRightButton = {
                    currentTime = totalTime
                    value = initialValue
                },
                onClickLeftButton = {
                    if (currentTime <= 0L) {
                        currentTime = totalTime
                        isTimerRunning = true
                    } else {
                        isTimerRunning = !isTimerRunning
                    }
                },
            )
        }
    }
}
