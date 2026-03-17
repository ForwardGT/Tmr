package tmr.impl.windows.setting_window

import androidx.compose.runtime.Composable
import androidx.compose.ui.window.rememberWindowState
import app.core.window.AppWindow
import app.core.window.TmrWindowWrapper
import app.core.window.WindowManager
import tmr.impl.windows.setting_window.components.SettingsView

@Composable
fun SettingsWindow(
    windowManager: WindowManager,
    window: AppWindow,
) {
    val windowState = rememberWindowState(
        size = window.size,
        position = window.position,
    )

    TmrWindowWrapper(
        windowManager = windowManager,
        windowState = windowState,
        window = window,
        resizable = false,
        alwaysOnTop = true,
        transparent = false,
        undecorated = false,
        content = {
            SettingsView()
        }
    )
}

