package tmr.impl.windows.setting_window.components

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import app.core.ui.components.TmrSwitch
import app.core.ui.components.TmrText
import app.core.ui.components.TmrTextField
import app.core.ui.resources.TmrColors
import app.core.ui.resources.TmrShapes
import org.koin.compose.viewmodel.koinViewModel
import tmr.impl.windows.setting_window.SettingsStore

private const val LABEL_ALWAYS_ON_TOP = "Поверх окон"
private const val LABEL_ENABLE_NOTIFICATIONS = "Показывать уведомления"
private const val LABEL_DEFAULT_SHUTDOWN_MINUTES = "Время до выключения"
private const val LABEL_TIMER_DESIGN = "Вид таймера"

@Composable
fun SettingsView(
    onClickCloseWindow: () -> Unit,
) {
    val store = koinViewModel<SettingsStore>()
    val state by store.state.collectAsState()
    val config = state.config

    Column(
        modifier = Modifier
            .fillMaxSize()
            .clip(TmrShapes.shape12)
            .border(2.dp, TmrColors.inactiveBar, TmrShapes.shape12),
    ) {
        TopControlWindowPanel(onClickCloseWindow = onClickCloseWindow)

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
            ElementSettingsTimerDesign(
                label = LABEL_TIMER_DESIGN,
                selectedDesign = config.timerDesign,
                onSelectDesign = store::updateTimerDesign,
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
