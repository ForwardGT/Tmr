package org.example.project.core.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import org.example.project.core.ui.resourses.TmrColors

@Composable
fun TmrButton(
    modifier: Modifier = Modifier,
    text: String,
    onClick: () -> Unit,
    colorGradient: List<Color> = TmrColors.defaultButtonGradient,
    isIcon: Boolean = false,
    resourcePath: String = "",
    isExitButton: Boolean = false,
) {
    val gradient = Brush.linearGradient(
        colors = colorGradient,
        start = Offset(0f, 0f),
        end = Offset(300f, 0f),
    )
    val shape = RoundedCornerShape(12.dp)

    Box(
        modifier = modifier
            .clip(shape)
            .background(gradient)
            .clickable(onClick = onClick)
            .then(if (!isExitButton) Modifier.padding(vertical = 8.dp, horizontal = 18.dp) else Modifier)
    ) {
        if (!isIcon) {
            Text(
                modifier = Modifier.align(Alignment.Center),
                text = text,
                color = TmrColors.mainText,
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold,
            )
        } else {
            Icon(
                contentDescription = null,
                painter = painterResource(resourcePath),
                modifier = Modifier.size(21.dp),
                tint = TmrColors.mainText
            )
        }
    }
}