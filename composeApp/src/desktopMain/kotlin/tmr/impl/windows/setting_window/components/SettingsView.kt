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
import app.core.ui.resources.TmrColors
import app.core.ui.resources.TmrShapes
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
            .border(2.dp, TmrColors.inactiveBar, TmrShapes.shape12),
    ) {
        TopControlWindowPanel(
            onClickCloseWindow = onClickCloseWindow
        )

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 12.dp, vertical = 6.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(6.dp)
        ) {
            ElementSettings(
                label = "Always on top",
                isChecked = config.alwaysOnTop,
                onChangeChecked = store::toggleAlwaysOnTop,
            )
            ElementSettings(
                label = "Enable notifications",
                isChecked = config.notificationsEnabled,
                onChangeChecked = store::toggleNotifications,
            )
        }
    }
}

@Composable
private fun ElementSettings(
    label: String,
    isChecked: Boolean,
    onChangeChecked: (Boolean) -> Unit,
) {
    Row(
        modifier = Modifier
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,
    ) {
        TmrText(
            text = label,
            fontSize = 13.sp,
        )
        TmrSwitch(
            isChecked = isChecked,
            onChangeChecked = onChangeChecked,
        )
    }
}

@Composable
private fun TopControlWindowPanel(
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
            text = "Settings",
            fontSize = 14.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(start = 6.dp)
        )

        Box(modifier = Modifier.clickable(onClick = onClickCloseWindow)) {
            TmrImage(
                modifier = Modifier.size(18.dp),
                tintColor = TmrColors.colorIconExit,
                model = Icons.Outlined.Close,
            )
        }
    }
}
