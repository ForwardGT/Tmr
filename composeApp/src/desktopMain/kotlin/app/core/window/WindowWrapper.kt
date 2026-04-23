package app.core.window

import androidx.compose.foundation.window.WindowDraggableArea
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.WindowState
import org.jetbrains.compose.resources.painterResource
import tmr.composeapp.generated.resources.Res
import tmr.composeapp.generated.resources.app_icon

@Composable
fun WindowWrapper(
    windowManager: WindowManager,
    windowState: WindowState,
    window: AppWindow,
    resizable: Boolean = false,
    alwaysOnTop: Boolean = true,
    undecorated: Boolean = true,
    transparent: Boolean = true,
    title: String = "Tmr",
    icon: Painter = painterResource(Res.drawable.app_icon),
    content: @Composable () -> Unit,
) {
    Window(
        state = windowState,
        onCloseRequest = { windowManager.close(window.id) },
        title = title,
        resizable = resizable,
        alwaysOnTop = alwaysOnTop,
        undecorated = undecorated,
        transparent = transparent,
        icon = icon,
    ) {
        WindowDraggableArea {
            content()
        }
    }
}