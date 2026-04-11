package tmr.impl.windows.timer_window.components

import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import tmr.impl.windows.timer_window.TimerStore
import tmr.impl.windows.timer_window.TmrState
import tmr.impl.windows.timer_window.TypeTimer
import tmr.impl.windows.timer_window.shutdown_timer.ShutdownTimer
import tmr.impl.windows.timer_window.work_timer.WorkTimer

@Composable
internal fun TimerSection(
    store: TimerStore,
    state: TmrState,
) {
    when (state.typeTimer) {
        TypeTimer.WorkTimer -> WorkTimer(
            modifier = Modifier.size(210.dp),
            store = store,
            state = state,
            timerDesign = state.timerDesign,
        )

        TypeTimer.ShutdownTimer -> ShutdownTimer(
            modifier = Modifier.size(210.dp),
            store = store,
            state = state,
            timerDesign = state.timerDesign,
        )
    }
}
