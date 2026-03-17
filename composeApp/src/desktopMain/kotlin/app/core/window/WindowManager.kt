package app.core.window

import androidx.compose.runtime.mutableStateListOf
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.ApplicationScope
import androidx.compose.ui.window.WindowPosition
import androidx.compose.ui.window.WindowState
import app.application.configurations.AppControl

class WindowManager(
    private val appControl: AppControl,
    private val applicationScope: ApplicationScope,
) {

    private val _windows = mutableStateListOf<AppWindow>()
    val windows = _windows

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
        appControl.closeWindow(windowState)
        applicationScope.exitApplication()
    }

    private fun initMainWindow() {

        val config = appControl.loadConfig()

        open(
            AppWindow.Timer(
                position = WindowPosition(
                    config.windowPositionX.dp,
                    config.windowPositionY.dp,
                )
            )
        )
    }

}
