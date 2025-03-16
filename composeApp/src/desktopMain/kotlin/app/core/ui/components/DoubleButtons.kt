package app.core.ui.components

import androidx.compose.foundation.layout.Row
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import app.core.ui.resourses.TmrColors
import tmr.composeapp.generated.resources.Res
import tmr.composeapp.generated.resources.pause_timer
import tmr.composeapp.generated.resources.start_timer
import tmr.composeapp.generated.resources.timeClear

@Composable
fun DoubleButtons(
    modifier: Modifier = Modifier,
    isTimerRunning: Boolean,
    currentTime: Int,
    onClickLeftButton: () -> Unit,
    onClickRightButton: () -> Unit,
) {
    val colorGradient = if (!isTimerRunning) TmrColors.defaultButtonGradient else TmrColors.dangerButtonGradient

    Row(modifier = modifier) {
        TmrButton(
            onClick = { onClickLeftButton() },
            colorGradientBackground = colorGradient,
            icon = when {
                isTimerRunning -> Res.drawable.pause_timer
                currentTime >= 0 -> Res.drawable.start_timer
                else -> Res.drawable.start_timer
            }
        )
        TmrSpacer(width = 10.dp)
        TmrButton(
            onClick = { onClickRightButton() },
            icon = Res.drawable.timeClear,
        )
    }
}
