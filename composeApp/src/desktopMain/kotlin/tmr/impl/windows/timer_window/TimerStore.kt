package tmr.impl.windows.timer_window

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import app.application.configurations.ConfigManager
import app.core.utils.extensions.reduce
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
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
    val shutdownTotalTime: Int = 60 * 60,
    val shutdownCurrentTime: Int = 60 * 60,
    val isShutdownTimerRunning: Boolean = false,
    val isShutdownEditMode: Boolean = false,
)

class TimerStore(
    private val getWeatherUseCase: GetWeatherUseCase,
    private val getUserLocationUseCase: GetUserLocationUseCase,
    private val configManager: ConfigManager,
) : ViewModel() {

    private var timerJob: Job? = null
    private var shutdownTimerJob: Job? = null

    private val _state = MutableStateFlow(TmrState())
    val state = _state.asStateFlow()

    init {
        applyDefaultShutdownTime(configManager.appConfig.value.defaultShutdownMinutes)
        observeConfig()
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

    fun toggleShutdownEditMode() {
        _state.reduce {
            copy(isShutdownEditMode = !isShutdownEditMode)
        }
    }

    fun updateShutdownMinutes(input: String?) {
        val minutes = input?.trim()?.toIntOrNull() ?: return closeShutdownEditMode()
        val totalSeconds = minutes * 60

        if (totalSeconds <= 0) {
            closeShutdownEditMode()
            return
        }

        shutdownTimerJob?.cancel()
        _state.reduce {
            copy(
                shutdownTotalTime = totalSeconds,
                shutdownCurrentTime = totalSeconds,
                isShutdownTimerRunning = false,
                isShutdownEditMode = false,
            )
        }
    }

    fun startPauseShutdownTimer() {
        if (_state.value.shutdownCurrentTime <= 0) {
            _state.reduce {
                copy(
                    shutdownCurrentTime = shutdownTotalTime,
                )
            }
        }

        if (_state.value.isShutdownTimerRunning) {
            pauseShutdownTimer()
            return
        }

        _state.reduce {
            copy(isShutdownTimerRunning = true)
        }

        shutdownTimerJob?.cancel()
        shutdownTimerJob = viewModelScope.launch {
            while (isActive && _state.value.shutdownCurrentTime > 0) {
                delay(1.seconds)
                _state.reduce {
                    copy(shutdownCurrentTime = shutdownCurrentTime - 1)
                }
            }

            if (_state.value.shutdownCurrentTime <= 0) {
                _state.reduce {
                    copy(isShutdownTimerRunning = false)
                }
                shutdownComputer()
            }
        }
    }

    fun resetShutdownTimer() {
        shutdownTimerJob?.cancel()
        _state.reduce {
            copy(
                shutdownCurrentTime = shutdownTotalTime,
                isShutdownTimerRunning = false,
            )
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

    private fun observeConfig() {
        viewModelScope.launch {
            configManager.appConfig.collectLatest { config ->
                if (_state.value.isShutdownTimerRunning) return@collectLatest
                applyDefaultShutdownTime(config.defaultShutdownMinutes)
            }
        }
    }

    private fun applyDefaultShutdownTime(defaultMinutes: Int) {
        val totalSeconds = defaultMinutes.coerceIn(1, 720) * 60
        _state.reduce {
            copy(
                shutdownTotalTime = totalSeconds,
                shutdownCurrentTime = totalSeconds,
            )
        }
    }

    private fun pauseShutdownTimer() {
        shutdownTimerJob?.cancel()
        _state.reduce {
            copy(isShutdownTimerRunning = false)
        }
    }

    private fun closeShutdownEditMode() {
        _state.reduce {
            copy(isShutdownEditMode = false)
        }
    }

    private fun shutdownComputer() {
        ProcessBuilder("cmd", "/c", "shutdown -s -t 0").start()
    }
}

enum class TypeTimer {
    WorkTimer, ShutdownTimer
}

enum class StateTimerManager {
    Start, Stop, Pause
}
