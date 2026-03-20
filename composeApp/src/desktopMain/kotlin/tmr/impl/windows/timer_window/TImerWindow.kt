package tmr.impl.windows.timer_window

import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.WindowPosition
import androidx.compose.ui.window.rememberWindowState
import app.core.window.AppWindow
import app.core.window.WindowManager
import app.core.window.WindowWrapper
import tmr.impl.windows.timer_window.components.TimerView

@Composable
fun TimerWindow(
    windowManager: WindowManager,
    window: AppWindow,
) {
    val windowState = rememberWindowState(
        size = window.size,
        position = window.position,
    )

    WindowWrapper(
        windowManager = windowManager,
        windowState = windowState,
        window = window,
        content = {
            TimerView(
                onClickCloseApp = {
                    windowManager.exitApplication(windowState)
                },
                onClickOptions = {
                    val settingsPosition = WindowPosition(
                        x = windowState.position.x + windowState.size.width + 10.dp,
                        y = windowState.position.y,
                    )
                    windowManager.open(
                        AppWindow.Settings(
                            position = settingsPosition,
                        )
                    )
                }
            )
        }
    )
}

