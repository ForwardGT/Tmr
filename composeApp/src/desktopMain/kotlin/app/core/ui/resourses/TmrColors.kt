package app.core.ui.resourses

import androidx.compose.ui.graphics.Color

object TmrColors {

    //Colors
    val inactiveComponent = Color.DarkGray.copy(.8f)
    val activeBar = Color(0xff00fff2)
    val mainText = Color(0xffe6d1fb)
    val colorIconExit = Color(0xffd8c7c7).copy(.8f)

    //Gradients
    val defaultButtonGradient = listOf(
        Color(0xFF6A11CB).copy(alpha = 0.7f),
        Color(0xffa80e66).copy(alpha = 0.7f)
    )
    val dangerButtonGradient = listOf(
        Color(0xffff0000).copy(alpha = 0.7f),
        Color(0xff536dec).copy(alpha = 0.7f)
    )
    val offButtonGradient = listOf(
        Color.Transparent,
        Color.Transparent
    )
}