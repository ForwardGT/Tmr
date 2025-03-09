package org.example.project.core.store

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import org.example.project.api.data.models.Weather
import org.example.project.api.data.repository.TmrRepository

data class TmrState(
    val isTimer: Boolean = false,
    val weather: Weather = Weather()
)

class TmrStore(private val repo: TmrRepository) : ViewModel() {

    private val _state = MutableStateFlow(TmrState())
    val state = _state.asStateFlow()

    init {
        getWeather()
    }

    fun switchTimer(value: Boolean) {
        _state.reduce { copy(isTimer = value) }
    }

    private fun getWeather() {
        viewModelScope.launch {
            while (true) {
                val weather = repo.getWeather()
                _state.reduce { copy(weather = weather) }
                delay(300000)
            }
        }
    }
}

private fun <T> MutableStateFlow<T>.reduce(transform: T.() -> T) {
    value = value.transform()
}