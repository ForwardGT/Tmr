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
import app.configurations.AppControl
import app.core.di.TmrKoin
import org.jetbrains.compose.resources.painterResource
import org.koin.compose.koinInject
import org.koin.core.context.GlobalContext
import tmr.composeapp.generated.resources.Res
import tmr.composeapp.generated.resources.logo
import tmr.impl.windows.main_window.MainStore
import tmr.impl.windows.main_window.MainView

fun main() = application {

    System.setProperty("skiko.renderApi", "OPENGL")

    if (GlobalContext.getOrNull() == null) {
        TmrKoin.initKoin(this)
    }

    val store = koinInject<MainStore>()
    val state by store.state.collectAsState()

    val appControl = koinInject<AppControl>()
    val config = appControl.loadConfig()

    val windowState = rememberWindowState(size = DpSize(250.dp, 250.dp)).apply {
        position = WindowPosition(config.windowPositionX.dp, config.windowPositionY.dp)
    }

    Window(
        onCloseRequest = { appControl.closeWindow(windowState) },
        title = "Tmr",
        state = windowState,
        resizable = false,
        alwaysOnTop = true,
        undecorated = true,
        transparent = true,
        icon = painterResource(Res.drawable.logo)
    ) {
        WindowDraggableArea {
            MainView(
                closeApp = { appControl.closeWindow(windowState) },
                state = state,
                store = store,
            )

        }
    }
}
