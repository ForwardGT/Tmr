package app.core.window

import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.WindowPosition

sealed class AppWindow {
    abstract val id: String
    abstract val size: DpSize
    abstract val position: WindowPosition

    data class Timer(
        override val id: String = "main-window",
        override val size: DpSize = DpSize(230.dp, 230.dp),
        override val position: WindowPosition = WindowPosition.PlatformDefault,
    ) : AppWindow()

    data class Settings(
        override val id: String = "settings-window",
        override val size: DpSize = DpSize(230.dp, 230.dp),
        override val position: WindowPosition = WindowPosition.PlatformDefault,
    ) : AppWindow()
}
