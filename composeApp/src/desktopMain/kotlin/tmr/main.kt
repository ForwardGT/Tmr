package tmr

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.window.WindowDraggableArea
import androidx.compose.runtime.*
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.WindowPosition
import androidx.compose.ui.window.application
import androidx.compose.ui.window.rememberWindowState
import app.application.configurations.AppControl
import app.application.start_application.StartApplication
import app.core.di.TmrKoin
import app.core.window.AppWindow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.debounce
import org.jetbrains.compose.resources.painterResource
import org.koin.compose.koinInject
import org.koin.core.context.GlobalContext
import tmr.composeapp.generated.resources.Res
import tmr.composeapp.generated.resources.logo
import tmr.impl.windows.main_window.MainView


fun main() = application {

    System.setProperty("skiko.renderApi", "OPENGL")

    if (GlobalContext.getOrNull() == null) {
        TmrKoin.initKoin(this)
    }

    StartApplication()

    val appControl = koinInject<AppControl>()
    val config = appControl.loadConfig()


    var mainWindowState: WindowPosition? by remember { mutableStateOf(null) }


    val windowsList: SnapshotStateList<AppWindow> = remember {
        mutableStateListOf(
            AppWindow.MainWindow(),
        )
    }

    windowsList.forEach { window ->

        val windowState = rememberWindowState(
            size = window.windowSize,
            position = when (window) {

                is AppWindow.MainWindow -> {
                    WindowPosition(config.windowPositionX.dp, config.windowPositionY.dp)
                }

                is AppWindow.SettingWindow -> {

                    mainWindowState?.let { wp ->
                        WindowPosition(wp.x + window.windowSize.width, wp.y)
                    } ?: WindowPosition.PlatformDefault
                }
            }
        )

        if (window is AppWindow.MainWindow) {
            LaunchedEffect(windowState.position) {
                snapshotFlow { windowState.position }.debounce(200).collectLatest { position ->
                    mainWindowState = position
                }
            }
        }

        Window(
            onCloseRequest = {
                windowsList.remove(window)
//                appControl.closeWindow(windowState)
            },
            title = "Tmr",
            state = windowState,
            resizable = window.resizable,
            alwaysOnTop = window.alwaysOnTop,
            undecorated = window.undecorated,
            transparent = window.transparent,
            icon = painterResource(Res.drawable.logo),
        ) {

            WindowDraggableArea {
                when (window) {
                    is AppWindow.MainWindow -> {
                        MainView(
                            onClickCloseApp = {
//                                this@application.exitApplication()
                                appControl.closeWindow(windowState)
                            },
                            onClickOptions = {
                                windowsList.add(AppWindow.SettingWindow())
                            }
                        )
                    }

                    is AppWindow.SettingWindow -> {
                        Box(Modifier.fillMaxSize().background(Color.Cyan.copy(alpha = 0.2f))) {

                        }
                    }
                }
            }
        }
    }
}
