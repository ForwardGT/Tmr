package tmr.impl.windows.setting_window.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Close
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import app.core.ui.components.TmrSwitch
import app.core.ui.components.TmrText
import app.core.ui.resourses.TmrColors
import app.core.ui.resourses.TmrShapes
import app.core.utils.remote_image.TmrImage
import org.koin.compose.viewmodel.koinViewModel
import tmr.impl.windows.setting_window.SettingsStore

@Composable
fun SettingsView(
    onClickCloseWindow: () -> Unit,
) {
    val store = koinViewModel<SettingsStore>()
    val config by store.config.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .clip(TmrShapes.shape12)
            .border(2.dp, TmrColors.inactiveBar, TmrShapes.shape12)
    ) {
        TopControlWindowPanel(
            onClickCloseWindow = onClickCloseWindow
        )

        TmrText(
            text = "Settings",
            fontSize = 14.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(start = 12.dp, top = 8.dp, bottom = 4.dp),
        )

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 12.dp, vertical = 6.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
        ) {
            TmrText(
                text = "Always on top",
                fontSize = 13.sp,
            )
            TmrSwitch(
                isChecked = config.alwaysOnTop,
                onChangeChecked = store::toggleAlwaysOnTop,
            )
        }
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
