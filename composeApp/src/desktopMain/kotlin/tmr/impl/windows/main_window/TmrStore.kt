package tmr.impl.windows.main_window

import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import app.core.configurations.AppConfig
import app.core.configurations.ConfigManager
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import tmr.api.models.Weather
import tmr.api.reposiroes.TmrRepository

data class TmrState(
    val weather: Weather = Weather(),
    val typeTimer: TypeTimer = TypeTimer.WorkTimer,
    val config: AppConfig? = null, // todo Придумать как реализовать без стэйта
)

class TmrStore(private val repo: TmrRepository) : ViewModel() {

    private val _state = MutableStateFlow(TmrState())
    val state = _state.asStateFlow()

    init {
        loadConfig()
        getWeather()
    }

    fun switchTimer(type: TypeTimer) {
        _state.reduce {
            copy(
                typeTimer = when (type) {
                    TypeTimer.WorkTimer -> TypeTimer.WorkTimer
                    TypeTimer.ShutdownTimer -> TypeTimer.ShutdownTimer
                }
            )
        }
    }

    fun saveConfig(windowPositionX: Float, windowPositionY: Float) {
        val config = AppConfig(
            windowPositionY = windowPositionY,
            windowPositionX = windowPositionX,
        )

        viewModelScope.launch {
            ConfigManager.saveConfig(config)
        }
    }

    private fun loadConfig() {
        viewModelScope.launch {
            val config = ConfigManager.loadConfig()
            println(config.windowPositionX.dp)
            _state.reduce {
                copy(config = config)
            }
        }
    }

    private fun getWeather() {
        viewModelScope.launch {
            while (true) {
                val weather = repo.getWeather()
                _state.reduce { copy(weather = weather) }
                delay(600000)
            }
        }
    }
}

private fun <T> MutableStateFlow<T>.reduce(transform: T.() -> T) {
    value = value.transform()
}

enum class TypeTimer {
    WorkTimer, ShutdownTimer
}