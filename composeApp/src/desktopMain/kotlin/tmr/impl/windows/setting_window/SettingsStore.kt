package tmr.impl.windows.setting_window

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import app.application.configurations.AppConfig
import app.application.configurations.ConfigManager
import app.application.configurations.TimerDesign
import app.core.utils.extensions.reduce
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

data class SettingsState(
    val config: AppConfig = AppConfig(),
)

class SettingsStore(
    private val configManager: ConfigManager,
) : ViewModel() {

    private val _state = MutableStateFlow(SettingsState(config = configManager.appConfig.value))
    val state = _state.asStateFlow()

    init {
        observeConfig()
    }

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

    fun updateDefaultShutdownMinutes(input: String?) {
        val minutes = input?.trim()?.toIntOrNull() ?: return
        if (minutes !in 1..720) return

        viewModelScope.launch {
            configManager.saveConfig { copy(defaultShutdownMinutes = minutes) }
        }
    }

    fun updateTimerDesign(design: TimerDesign) {
        viewModelScope.launch {
            configManager.saveConfig { copy(timerDesign = design) }
        }
    }

    private fun observeConfig() {
        viewModelScope.launch {
            configManager.appConfig.collectLatest { config ->
                _state.reduce { copy(config = config) }
            }
        }
    }
}
