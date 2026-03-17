package app.core.window

import androidx.compose.runtime.Composable
import androidx.compose.runtime.key
import org.koin.compose.koinInject
import tmr.impl.windows.setting_window.SettingsWindow
import tmr.impl.windows.timer_window.TimerWindow

@Composable
fun WindowHost() {

    val windowManager: WindowManager = koinInject()

    windowManager.windows.forEach { window ->
        key(window.id) {
            when (window) {
                is AppWindow.Timer -> {
                    TimerWindow(
                        window = window,
                        windowManager = windowManager,
                    )

                }

                is AppWindow.Settings -> SettingsWindow(
                    window = window,
                    windowManager = windowManager,
                )
            }
        }
    }
}

