package tmr.impl.windows.timer_window

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import org.koin.compose.viewmodel.koinViewModel
import tmr.impl.windows.timer_window.components.HeaderControlButton
import tmr.impl.windows.timer_window.components.SwitchingText
import tmr.impl.windows.timer_window.components.TimerSection
import tmr.impl.windows.timer_window.components.WeatherBlock

@Composable
fun TimerView(
    onClickCloseApp: () -> Unit,
    onClickOptions: () -> Unit,
) {
    val store = koinViewModel<TimerStore>()
    val state by store.state.collectAsState()

    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center,
    ) {
        HeaderControlButton(
            state = state,
            onClickSwitch = store::switchTimer,
            onClickCloseApp = onClickCloseApp,
            modifier = Modifier.align(Alignment.TopCenter),
            onClickOptions = onClickOptions,
        )
        WeatherBlock(
            state = state,
            modifier = Modifier.align(Alignment.TopCenter)
        )
        SwitchingText(
            lastUpdate = state.weather.lastUpdate,
            city = state.userLocation.city,
            modifier = Modifier.align(Alignment.TopCenter)
        )
        TimerSection(
            store = store,
            state = state,
        )
    }
}
