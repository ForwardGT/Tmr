package org.example.project

import androidx.compose.foundation.window.WindowDraggableArea
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import androidx.compose.ui.window.rememberWindowState

fun main() = application {
    val state = rememberWindowState(size = DpSize(250.dp, 250.dp))

    Window(
        onCloseRequest = ::exitApplication,
        title = "Tmr",
        state = state,
        resizable = false,
        alwaysOnTop = true,
        undecorated = true,
        transparent = true,
        icon = painterResource("logo.png")
    ) {
        WindowDraggableArea {
            App(::exitApplication)
        }
    }
}