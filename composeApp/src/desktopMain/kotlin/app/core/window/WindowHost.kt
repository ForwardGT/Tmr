package app.core.window

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.key
import app.application.configurations.ConfigManager
import org.koin.compose.koinInject
import tmr.impl.windows.setting_window.SettingsWindow
import tmr.impl.windows.timer_window.TimerWindow

@Composable
fun WindowHost() {

    val windowManager: WindowManager = koinInject()
    val configManager: ConfigManager = koinInject()
    val config by configManager.appConfig.collectAsState()

    windowManager.windows.forEach { window ->
        key(window.id) {
            when (window) {
                is AppWindow.Timer -> {
                    TimerWindow(
                        window = window,
                        windowManager = windowManager,
                        alwaysOnTop = config.alwaysOnTop,
                    )
                }

                is AppWindow.Settings -> {
                    SettingsWindow(
                        window = window,
                        windowManager = windowManager,
                    )
                }
            }
        }
    }
}
