package app.core.ui.components

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp

@Composable
fun TimeDisplay(
    valueSecond: Int,
    modifier: Modifier = Modifier,
) {
    val hours = valueSecond / 3600
    val minutes = (valueSecond % 3600) / 60
    val seconds = valueSecond % 60

    TmrText(
        modifier = modifier,
        text = String.format("%02d:%02d:%02d", hours, minutes, seconds),
        fontSize = 32.sp,
        fontWeight = FontWeight.Bold,
    )
}

@Preview
@Composable
private fun TimeDisplayPreview() {
    TimeDisplay(valueSecond = 3723)
}
