package tmr

import androidx.compose.foundation.window.WindowDraggableArea
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.WindowPosition
import androidx.compose.ui.window.application
import androidx.compose.ui.window.rememberWindowState
import tmr.impl.windows.main_window.App
import app.core.TmrKoin
import tmr.impl.windows.main_window.TmrStore
import org.jetbrains.compose.resources.painterResource
import org.koin.compose.koinInject
import org.koin.core.context.GlobalContext
import tmr.composeapp.generated.resources.Res
import tmr.composeapp.generated.resources.logo

fun main() = application {

    System.setProperty("skiko.renderApi", "OPENGL")

    if (GlobalContext.getOrNull() == null) {
        TmrKoin.initKoin()
    }

    val store: TmrStore = koinInject<TmrStore>()
    val state by store.state.collectAsState()

    val windowState = rememberWindowState(size = DpSize(250.dp, 250.dp))

    state.config?.also {
        windowState.position = WindowPosition(x = it.windowPositionX.dp, y = it.windowPositionY.dp)
    }

    Window(
        onCloseRequest = {
            exitApplication()
        },
        title = "Tmr",
        state = windowState,
        resizable = false,
        alwaysOnTop = true,
        undecorated = true,
        transparent = true,
        icon = painterResource(Res.drawable.logo)
    ) {
        WindowDraggableArea {
            App(closeApp = {
                store.saveConfig(windowState.position.x.value, windowState.position.y.value)
                exitApplication()
            }, store, state)
        }
    }
}