package app.core.window

import androidx.compose.runtime.mutableStateListOf
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.ApplicationScope
import androidx.compose.ui.window.WindowPosition
import androidx.compose.ui.window.WindowState
import app.application.configurations.ConfigManager
import app.core.utils.log.TmrLogger
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class WindowManager(
    private val applicationScope: ApplicationScope,
    private val configManager: ConfigManager,
) {
    private val _windows = mutableStateListOf<AppWindow>()
    val windows = _windows

    private val scope = CoroutineScope(Dispatchers.IO + CoroutineExceptionHandler { _, e ->
        TmrLogger.e("WindowManager", e, "Unhandled scope error")
    })

    init {
        initMainWindow()
    }

    fun open(window: AppWindow) {
        if (_windows.none { it.id == window.id }) {
            _windows.add(window)
        }
    }

    fun close(id: String) {
        _windows.removeAll { it.id == id }
    }

    fun exitApplication(windowState: WindowState) {
        scope.launch {
            configManager.saveConfig {
                copy(
                    windowPositionX = windowState.position.x.value,
                    windowPositionY = windowState.position.y.value,
                )
            }
            applicationScope.exitApplication()
        }
    }

    private fun initMainWindow() {
        open(
            AppWindow.Timer(
                position = WindowPosition(
                    configManager.appConfig.value.windowPositionX.dp,
                    configManager.appConfig.value.windowPositionY.dp,
                )
            )
        )
    }

}
