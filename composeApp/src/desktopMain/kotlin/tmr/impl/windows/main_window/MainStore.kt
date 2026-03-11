package tmr.impl.windows.main_window

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import app.core.utils.extensions.reduce
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch
import tmr.api.models.Weather
import tmr.api.usecases.GetWeatherUseCase
import kotlin.time.Duration.Companion.minutes
import kotlin.time.Duration.Companion.seconds

data class TmrState(
    val weather: Weather = Weather(),
    val typeTimer: TypeTimer = TypeTimer.WorkTimer,
    val currentWorkTime: Int = 0,
    val timerIsRunning: Boolean = false,
)

class MainStore(private val getWeatherUseCase: GetWeatherUseCase) : ViewModel() {

    private var timerJob: Job? = null

    private val _state = MutableStateFlow(TmrState())
    val state = _state.asStateFlow()

    init {
        getWeather()
    }

    fun switchTimer(type: TypeTimer) {
        _state.reduce {
            copy(typeTimer = type)
        }
    }

    fun startStopWorkTimer(stateManager: StateTimerWorkManager) {
        when (stateManager) {

            StateTimerWorkManager.Start -> {
                if (timerJob?.isActive == true) return

                timerJob = viewModelScope.launch {

                    _state.reduce {
                        copy(timerIsRunning = true)
                    }

                    while (isActive) {
                        _state.reduce {
                            copy(currentWorkTime = currentWorkTime + 1)
                        }
                        delay(1.seconds)
                    }

                }
            }

            StateTimerWorkManager.Pause -> {
                _state.reduce {
                    copy(timerIsRunning = false)
                }
                timerJob?.cancel()
            }

            StateTimerWorkManager.Stop -> {
                _state.reduce {
                    copy(
                        currentWorkTime = 0,
                        timerIsRunning = false
                    )
                }
                timerJob?.cancel()
            }
        }
    }

    private fun getWeather() {
        viewModelScope.launch {
            while (true) {
                val weather = getWeatherUseCase()
                _state.reduce { copy(weather = weather) }
                delay(20.minutes)
            }
        }
    }
}

enum class TypeTimer {
    WorkTimer, ShutdownTimer
}

enum class StateTimerWorkManager {
    Start, Stop, Pause
}
