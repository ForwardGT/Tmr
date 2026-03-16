package app.core.window

import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp
import java.util.*

sealed interface AppWindow {

    val id: String
    val windowSize: DpSize
    val alwaysOnTop: Boolean get() = false
    val transparent: Boolean get() = false
    val undecorated: Boolean get() = false
    val resizable: Boolean get() = false

    data class MainWindow(
        override val id: String = UUID.randomUUID().toString(),
        override val windowSize: DpSize = DpSize(250.dp, 250.dp),
        override val alwaysOnTop: Boolean = true,
        override val transparent: Boolean = true,
        override val undecorated: Boolean = true,
    ) : AppWindow

    data class SettingWindow(
        override val id: String = UUID.randomUUID().toString(),
        override val windowSize: DpSize = DpSize(300.dp, 300.dp),
        override val alwaysOnTop: Boolean = true,
//        val startWindowPosition: WindowPosition? = null,
    ) : AppWindow
}


