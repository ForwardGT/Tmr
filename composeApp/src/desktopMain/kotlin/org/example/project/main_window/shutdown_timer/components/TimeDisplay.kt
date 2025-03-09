package org.example.project.main_window.shutdown_timer.components


import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import org.example.project.core.ui.resourses.TmrColors

@Composable
fun TimeDisplay(
    valueSecond: Int,
    modifier: Modifier = Modifier,
) {
    val hours = valueSecond / 3600
    val minutes = (valueSecond % 3600) / 60
    val seconds = valueSecond % 60

    Text(
        modifier = modifier,
        text = String.format("%02d:%02d:%02d", hours, minutes, seconds),
        fontSize = 32.sp,
        fontWeight = FontWeight.Bold,
        color = TmrColors.mainText,
    )
}