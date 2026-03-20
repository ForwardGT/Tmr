package tmr.impl.windows.setting_window.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Close
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import app.core.ui.resourses.TmrColors
import app.core.ui.resourses.TmrShapes
import app.core.utils.remote_image.TmrImage

@Composable
fun SettingsView(
    onClickCloseWindow: () -> Unit,
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .clip(TmrShapes.shape12)
            .border(2.dp, TmrColors.inactiveBar, TmrShapes.shape12)
    ) {
        TopControlWindowPanel(
            onClickCloseWindow = onClickCloseWindow
        )
    }
}

@Composable
fun TopControlWindowPanel(
    onClickCloseWindow: () -> Unit,
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(TmrColors.inactiveComponent)
    ) {
        Spacer(Modifier.weight(1f))
        Box(
            modifier = Modifier
                .clickable(onClick = onClickCloseWindow)
                .padding(2.dp),
        ) {
            TmrImage(
                modifier = Modifier.size(18.dp),
                tintColor = TmrColors.colorIconExit,
                model = Icons.Outlined.Close,
            )
        }
    }
}
