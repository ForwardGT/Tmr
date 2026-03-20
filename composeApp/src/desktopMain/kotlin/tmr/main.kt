package tmr

import androidx.compose.ui.window.application
import app.core.di.TmrKoin.initKoin
import app.core.window.WindowHost
import org.koin.compose.KoinApplication

fun main() = application {

    System.setProperty("skiko.renderApi", "OPENGL")

    KoinApplication(
        application = {
            initKoin(this@application)
        }
    ) {
        WindowHost()
    }
}
