package app.core.ui.components

import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.sp
import app.core.ui.resources.TmrColors

@Composable
fun TmrText(
    text: String,
    modifier: Modifier = Modifier,
    fontSize: TextUnit = 16.sp,
    color: Color = TmrColors.mainText,
    fontWeight: FontWeight? = null,
    textAlign: TextAlign? = null,
    alpha: Float = 1f,
) {
    Text(
        text = text,
        modifier = modifier,
        fontSize = fontSize,
        color = if (alpha < 1f) color.copy(alpha = alpha) else color,
        fontWeight = fontWeight,
        textAlign = textAlign,
    )
}
