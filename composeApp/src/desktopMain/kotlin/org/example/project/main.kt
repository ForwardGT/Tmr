package org.example.project

import androidx.compose.foundation.window.WindowDraggableArea
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.WindowPosition
import androidx.compose.ui.window.application
import androidx.compose.ui.window.rememberWindowState
import org.example.project.core.utils.setTaskbarIcon
import org.koin.compose.koinInject
import org.koin.core.context.GlobalContext

fun main() = application {

    System.setProperty("skiko.renderApi", "OPENGL")
    setTaskbarIcon("logo.png")

    if (GlobalContext.getOrNull() == null) { TmrKoin.initKoin() }

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
        icon = painterResource("logo.png")
    ) {
        WindowDraggableArea {
            App(closeApp = {
                store.saveConfig(windowState.position.x.value, windowState.position.y.value)
                exitApplication()
            }, store, state)
        }
    }
}