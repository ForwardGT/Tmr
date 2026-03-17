package app.application.configurations

import androidx.compose.ui.window.ApplicationScope
import androidx.compose.ui.window.WindowState
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

interface AppControl {

    fun closeWindow(windowState: WindowState)

    fun loadConfig(): AppConfig
}

class AppControlImpl(
    private val applicationScope: ApplicationScope,
    private val configManager: ConfigManager,
) : AppControl {

    private val scope = CoroutineScope(Dispatchers.IO)

    override fun closeWindow(windowState: WindowState) {
        scope.launch {
            configManager.saveConfig(windowState.position)
        }
        applicationScope.exitApplication()
    }

    override fun loadConfig() = configManager.appConfig.value

}
