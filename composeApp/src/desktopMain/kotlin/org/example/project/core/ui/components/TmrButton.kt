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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import org.example.project.core.ui.resourses.TmrColors
import org.jetbrains.compose.resources.DrawableResource
import org.jetbrains.compose.resources.painterResource

@Composable
fun TmrButton(
    modifier: Modifier = Modifier,
    text: String = "",
    onClick: () -> Unit,
    colorGradientBackground: List<Color> = TmrColors.defaultButtonGradient,
    colorText: Color = TmrColors.mainText,
    colorIcon: Color = TmrColors.mainText,
    icon: DrawableResource? = null,
    isExitButton: Boolean = false,
) {
    val gradient = Brush.linearGradient(
        colors = colorGradientBackground,
        start = Offset(0f, 0f),
        end = Offset(300f, 0f),
    )
    val shape = RoundedCornerShape(12.dp)

    Box(
        contentAlignment = Alignment.Center,
        modifier = modifier
            .clip(shape)
            .background(gradient)
            .clickable(onClick = onClick)
            .then(if (!isExitButton) Modifier.padding(vertical = 8.dp, horizontal = 18.dp) else Modifier)
    ) {
        if (icon == null) {
            Text(
                modifier = Modifier.align(Alignment.Center),
                text = text,
                color = colorText,
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold,
            )
        } else {
            Icon(
                contentDescription = null,
                painter = painterResource(icon),
                modifier = Modifier.size(21.dp),
                tint = colorIcon
            )
        }
    }
}