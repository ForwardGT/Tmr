package org.example.project.main_window.work_timer.components

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import org.example.project.core.ui.components.TmrButton
import org.example.project.core.ui.resourses.TmrColors
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
        Spacer(modifier = Modifier.padding(start = 10.dp))
        TmrButton(
            onClick = { onClickRightButton() },
            icon = Res.drawable.timeClear,
        )
    }
}
