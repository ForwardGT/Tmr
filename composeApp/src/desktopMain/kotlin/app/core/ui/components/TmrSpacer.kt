package app.core.ui.components

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.foundation.background

@Composable
fun TmrSpacer(height: Dp = 0.dp, width: Dp = 0.dp) {
    Spacer(
        modifier = Modifier.size(
            width = width,
            height = height
        )
    )
}

@Preview
@Composable
private fun TmrSpacerPreview() {
    Spacer(
        modifier = Modifier
            .size(width = 48.dp, height = 10.dp)
            .background(Color.Gray),
    )
}
