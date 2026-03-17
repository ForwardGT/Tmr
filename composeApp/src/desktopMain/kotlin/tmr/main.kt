package tmr

import androidx.compose.ui.window.application
import app.application.start_application.StartApplication
import app.core.di.TmrKoin
import org.koin.core.context.GlobalContext

fun main() = application {

    System.setProperty("skiko.renderApi", "OPENGL")

    if (GlobalContext.getOrNull() == null) {
        TmrKoin.initKoin(this)
    }

    StartApplication()

}
