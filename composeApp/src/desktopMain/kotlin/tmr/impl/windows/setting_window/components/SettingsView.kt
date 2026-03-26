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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import app.core.ui.components.TmrSwitch
import app.core.ui.components.TmrText
import app.core.ui.components.TmrTextField
import app.core.ui.resources.TmrColors
import app.core.ui.resources.TmrShapes
import app.core.utils.remote_image.TmrImage
import org.koin.compose.viewmodel.koinViewModel
import tmr.impl.windows.setting_window.SettingsStore

private const val LABEL_ALWAYS_ON_TOP = "Always on top"
private const val LABEL_ENABLE_NOTIFICATIONS = "Enable notifications"
private const val LABEL_SETTINGS_TITLE = "Settings"
private const val LABEL_DEFAULT_SHUTDOWN_MINUTES = "Default shutdown min"

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
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            ElementSettings(
                label = LABEL_ALWAYS_ON_TOP,
                isChecked = config.alwaysOnTop,
                onChangeChecked = store::toggleAlwaysOnTop,
            )
            ElementSettings(
                label = LABEL_ENABLE_NOTIFICATIONS,
                isChecked = config.notificationsEnabled,
                onChangeChecked = store::toggleNotifications,
            )
            ElementSettingsTextField(
                label = LABEL_DEFAULT_SHUTDOWN_MINUTES,
                value = config.defaultShutdownMinutes,
                onSubmit = store::updateDefaultShutdownMinutes,
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
private fun ElementSettingsTextField(
    label: String,
    value: Int,
    onSubmit: (String?) -> Unit,
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,
    ) {
        TmrText(
            text = label,
            fontSize = 13.sp,
        )
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(6.dp),
        ) {
            TmrText(
                text = value.toString(),
                textAlign = TextAlign.End,
                fontSize = 13.sp,
            )
            TmrTextField(submit = onSubmit)
        }
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
            text = LABEL_SETTINGS_TITLE,
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
