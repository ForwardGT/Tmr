package app.core.ui.components

import androidx.compose.runtime.Composable
import app.core.ui.resourses.TmrColors
import app.core.ui.resourses.TmrColors.colorIconExit
import tmr.composeapp.generated.resources.Res
import tmr.composeapp.generated.resources.exit

@Composable
fun ExitButton(
    onClickExit: () -> Unit,
) {
    TmrButton(
        text = "",
        onClick = onClickExit,
        isExitButton = true,
        icon = Res.drawable.exit,
        colorGradientBackground = TmrColors.offButtonGradient,
        colorIcon = colorIconExit,
    )
}