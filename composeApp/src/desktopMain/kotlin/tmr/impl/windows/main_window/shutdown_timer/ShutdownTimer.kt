package tmr.impl.windows.main_window.shutdown_timer

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import app.core.ui.components.DoubleButtons
import app.core.ui.components.TimeDisplay
import app.core.ui.components.TmrButton
import app.core.ui.resourses.TmrColors
import app.core.ui.resourses.TmrColors.colorIconExit
import kotlinx.coroutines.delay
import tmr.composeapp.generated.resources.Res
import tmr.composeapp.generated.resources.gear
import tmr.impl.windows.main_window.shutdown_timer.components.TmrTextField

@Composable
fun ShutdownTimer(
    modifier: Modifier = Modifier,
    initialValue: Float = 1f,
    strokeWidth: Dp = 5.dp,
) {
    var totalTime by remember { mutableStateOf(3600L * 1000L) }
    var isEdit by remember { mutableStateOf(false) }
    var currentTime by remember { mutableStateOf(totalTime) }
    var isTimerRunning by remember { mutableStateOf(false) }
    var value by remember { mutableStateOf(initialValue) }

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

    Box(
        modifier = modifier,
        contentAlignment = Alignment.Center,
    ) {

        Canvas(modifier = Modifier.size(200.dp)) {
            val size = size.copy(width = size.width, height = size.height)
            drawArc(
                color = TmrColors.inactiveComponent,
                startAngle = -215f,
                sweepAngle = 250f,
                useCenter = false,
                size = size,
                style = Stroke(strokeWidth.toPx(), cap = StrokeCap.Round)
            )
            drawArc(
                color = TmrColors.activeBar,
                startAngle = -215f,
                sweepAngle = 250f * value,
                useCenter = false,
                size = size,
                style = Stroke(strokeWidth.toPx(), cap = StrokeCap.Round)
            )
        }

        if (isEdit) {
            Box(modifier = Modifier.padding(bottom = 50.dp)) {
                TmrTextField { inputText ->
                    totalTime = (inputText.toIntOrNull()?.times(60 * 1000L)) ?: totalTime
                    currentTime = totalTime
                    value = initialValue
                    isEdit = false
                }
            }

        } else {
            Row(
                horizontalArrangement = Arrangement.Center,
                modifier = Modifier.padding(start = 14.dp)
            ) {
                TimeDisplay(
                    modifier = Modifier.padding(bottom = 50.dp),
                    valueSecond = (currentTime / 1000L).toInt()
                )

                TmrButton(
                    modifier = Modifier.size(14.dp),
                    text = "",
                    icon = Res.drawable.gear,
                    onClick = { isEdit = !isEdit },
                    colorGradientBackground = TmrColors.offButtonGradient,
                    colorIcon = colorIconExit,
                    isExitButton = true
                )
            }

        }

        DoubleButtons(
            modifier = Modifier.align(Alignment.BottomCenter).padding(bottom = 52.dp),
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
            }
        )
    }
}
