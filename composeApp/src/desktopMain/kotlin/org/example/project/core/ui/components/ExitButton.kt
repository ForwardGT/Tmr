package org.example.project.core.ui.components

import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import org.example.project.core.ui.resourses.TmrColors

@Composable
fun ExitButton(
    onClickExit: () -> Unit
) {
    TmrButton(
        modifier = Modifier.padding(end = 8.dp, top = 14.dp),
        text = "",
        onClick = onClickExit,
        isIcon = true,
        isExitButton = true,
        resourcePath = "exit.png",
        colorGradient = TmrColors.offButtonGradient
    )
}