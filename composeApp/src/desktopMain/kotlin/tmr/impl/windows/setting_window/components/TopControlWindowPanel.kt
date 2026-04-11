package tmr.impl.windows.setting_window.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Close
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import app.core.ui.components.TmrText
import app.core.ui.resources.TmrColors
import app.core.ui.resources.TmrShapes
import app.core.utils.remote_image.TmrImage

private const val LABEL_SETTINGS_TITLE = "Настройки"

@Composable
internal fun TopControlWindowPanel(
    onClickCloseWindow: () -> Unit,
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(TmrColors.inactiveComponent)
            .padding(horizontal = 6.dp, vertical = 6.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween,
    ) {
        TmrText(
            text = LABEL_SETTINGS_TITLE,
            fontSize = 14.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(start = 6.dp),
        )

        Box(
            modifier = Modifier
                .clip(TmrShapes.shape12)
                .clickable(onClick = onClickCloseWindow),
        ) {
            TmrImage(
                modifier = Modifier.size(18.dp),
                tintColor = TmrColors.colorIconExit,
                model = Icons.Outlined.Close,
            )
        }
    }
}
