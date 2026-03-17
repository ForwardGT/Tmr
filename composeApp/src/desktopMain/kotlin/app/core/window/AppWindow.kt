package app.core.window

import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.WindowPosition

sealed class AppWindow {
    abstract val id: String
    abstract val size: DpSize
    abstract val position: WindowPosition
    open val alwaysOnTop: Boolean = false
    open val transparent: Boolean = false
    open val undecorated: Boolean = false
    open val resizable: Boolean = false

    data class Timer(
        override val id: String = "main-window",
        override val size: DpSize = DpSize(250.dp, 250.dp),
        override val position: WindowPosition = WindowPosition.PlatformDefault,
        override val alwaysOnTop: Boolean = true,
        override val transparent: Boolean = true,
        override val undecorated: Boolean = true,
    ) : AppWindow()

    data class Settings(
        override val id: String = "settings-window",
        override val size: DpSize = DpSize(300.dp, 300.dp),
        override val position: WindowPosition = WindowPosition.PlatformDefault,
        override val alwaysOnTop: Boolean = true,
    ) : AppWindow()
}
