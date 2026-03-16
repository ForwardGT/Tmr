package app.application.start_application

import androidx.compose.runtime.Composable
import org.koin.compose.koinInject

@Composable
fun StartApplication() {
    val store = koinInject<StartApplicationStore>()

}