package tmr.impl.windows.setting_window

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import app.application.configurations.ConfigManager
import kotlinx.coroutines.launch

class SettingsStore(
    private val configManager: ConfigManager,
) : ViewModel() {

    val config = configManager.appConfig

    fun toggleAlwaysOnTop(enabled: Boolean) {
        viewModelScope.launch {
            configManager.saveConfig { copy(alwaysOnTop = enabled) }
        }
    }

    fun toggleNotifications(enabled: Boolean) {
        viewModelScope.launch {
            configManager.saveConfig { copy(notificationsEnabled = enabled) }
        }
    }
}
