package tmr.impl.windows.timer_window.components

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.ui.Modifier
import app.application.configurations.TimerDesign
import app.core.ui.components.DoubleButtons
import tmr.impl.windows.timer_window.StateTimerManager
import tmr.impl.windows.timer_window.TimerStore
import tmr.impl.windows.timer_window.TmrState

@Composable
fun WorkTimerView(
    modifier: Modifier = Modifier,
    store: TimerStore,
    state: TmrState,
    timerDesign: TimerDesign,
) {
    val alpha = remember { Animatable(0.1f) }
    val currentState by rememberUpdatedState(state.stateTimerManager)

    LaunchedEffect(state.stateTimerManager) {
        when (state.stateTimerManager) {
            StateTimerManager.Start -> {
                alpha.animateTo(
                    targetValue = 1f,
                    animationSpec = tween(500),
                )
            }

            StateTimerManager.Pause -> {
                while (currentState == StateTimerManager.Pause) {
                    alpha.animateTo(targetValue = 0.1f, animationSpec = tween(1200))
                    alpha.animateTo(targetValue = 1f, animationSpec = tween(1200))
                }
            }

            StateTimerManager.Stop -> {
                alpha.animateTo(
                    targetValue = 0.1f,
                    animationSpec = tween(500),
                )
            }
        }
    }

    when (timerDesign) {
        TimerDesign.Minimal -> WorkTimerMinimalDesign(
            modifier = modifier,
            state = state,
            store = store,
            alpha = alpha.value,
        )

        TimerDesign.Focus -> WorkTimerFocusDesign(
            modifier = modifier,
            state = state,
            store = store,
        )

        TimerDesign.Radar -> WorkTimerRadarDesign(
            modifier = modifier,
            state = state,
            store = store,
        )
    }
}

@Composable
internal fun WorkTimerButtons(
    modifier: Modifier,
    state: TmrState,
    store: TimerStore,
) {
    DoubleButtons(
        modifier = modifier,
        onClickLeftButton = { onWorkPrimaryAction(store, state.stateTimerManager) },
        onClickRightButton = { store.startStopWorkTimer(StateTimerManager.Stop) },
        isTimerRunning = state.stateTimerManager == StateTimerManager.Start,
        currentTime = state.currentWorkTime,
    )
}

private fun onWorkPrimaryAction(
    store: TimerStore,
    stateTimerManager: StateTimerManager,
) {
    if (stateTimerManager != StateTimerManager.Start) {
        store.startStopWorkTimer(StateTimerManager.Start)
    } else {
        store.startStopWorkTimer(StateTimerManager.Pause)
    }
}
