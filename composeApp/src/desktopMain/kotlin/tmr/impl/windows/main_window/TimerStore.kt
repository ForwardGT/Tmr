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
import tmr.api.models.UserLocation
import tmr.api.models.Weather
import tmr.api.usecases.GetUserLocationUseCase
import tmr.api.usecases.GetWeatherUseCase
import kotlin.time.Duration.Companion.minutes
import kotlin.time.Duration.Companion.seconds

data class TmrState(
    val weather: Weather = Weather(),
    val userLocation: UserLocation = UserLocation(),
    val typeTimer: TypeTimer = TypeTimer.WorkTimer,
    val currentWorkTime: Int = 0,
    val stateTimerManager: StateTimerManager = StateTimerManager.Stop,
    )

class TimerStore(
    private val getWeatherUseCase: GetWeatherUseCase,
    private val getUserLocationUseCase: GetUserLocationUseCase,
) : ViewModel() {

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

    fun startStopWorkTimer(stateManager: StateTimerManager) {
        when (stateManager) {

            StateTimerManager.Start -> {
                if (timerJob?.isActive == true) return

                timerJob = viewModelScope.launch {

                    _state.reduce {
                        copy(stateTimerManager = StateTimerManager.Start)
                    }

                    while (isActive) {
                        _state.reduce {
                            copy(currentWorkTime = currentWorkTime + 1)
                        }
                        delay(1.seconds)
                    }

                }
            }

            StateTimerManager.Pause -> {
                _state.reduce {
                    copy(stateTimerManager = StateTimerManager.Pause)
                }
                timerJob?.cancel()
            }

            StateTimerManager.Stop -> {
                _state.reduce {
                    copy(
                        currentWorkTime = 0,
                        stateTimerManager = StateTimerManager.Stop
                    )
                }
                timerJob?.cancel()
            }
        }
    }

    private fun getWeather() {
        viewModelScope.launch {

            val userLocation = getUserLocationUseCase()
            _state.reduce { copy(userLocation = userLocation) }

            while (true) {
                val weather = getWeatherUseCase(
                    longitude = userLocation.longitude,
                    latitude = userLocation.latitude,
                )
                _state.reduce { copy(weather = weather) }
                delay(20.minutes)
            }
        }
    }
}

enum class TypeTimer {
    WorkTimer, ShutdownTimer
}

enum class StateTimerManager {
    Start, Stop, Pause
}
