package org.example.project.core.ui.components

import androidx.compose.runtime.Composable
import org.example.project.core.ui.resourses.TmrColors
import org.example.project.core.ui.resourses.TmrColors.colorIconExit

@Composable
fun ExitButton(
    onClickExit: () -> Unit
) {
    TmrButton(
//        modifier = Modifier.padding(end = 8.dp, top = 14.dp),
        text = "",
        onClick = onClickExit,
        isExitButton = true,
        iconResourcePath = "exit.png",
        colorGradientBackground = TmrColors.offButtonGradient,
        colorIcon = colorIconExit
    )
}