package tmr.impl.windows.main_window

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import app.core.utils.extensions.reduce
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import tmr.api.models.Weather
import tmr.api.usecases.GetWeatherUseCase

data class TmrState(
    val weather: Weather = Weather(),
    val typeTimer: TypeTimer = TypeTimer.WorkTimer,
)

class MainStore(private val getWeatherUseCase: GetWeatherUseCase) : ViewModel() {

    private val _state = MutableStateFlow(TmrState())
    val state = _state.asStateFlow()

    init {
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

    private fun getWeather() {
        viewModelScope.launch {
            while (true) {
                val weather = getWeatherUseCase()
                _state.reduce { copy(weather = weather) }
                delay(1000000)
            }
        }
    }
}

enum class TypeTimer {
    WorkTimer, ShutdownTimer
}
