package tmr.impl.windows.setting_window

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import app.core.ui.resources.TmrColors
import app.core.ui.resources.TmrShapes
import org.koin.compose.viewmodel.koinViewModel
import tmr.impl.windows.setting_window.components.ElementSettings
import tmr.impl.windows.setting_window.components.ElementSettingsTextField
import tmr.impl.windows.setting_window.components.ElementSettingsTimerDesign
import tmr.impl.windows.setting_window.components.TopControlWindowPanel

private const val LABEL_ALWAYS_ON_TOP = "Поверх окон"
private const val LABEL_ENABLE_NOTIFICATIONS = "Показывать уведомления"
private const val LABEL_DEFAULT_SHUTDOWN_MINUTES = "Время до выключения"
private const val LABEL_TIMER_DESIGN = "Тема таймера"

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
