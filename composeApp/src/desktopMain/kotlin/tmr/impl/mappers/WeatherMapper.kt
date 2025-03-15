package tmr.impl.mappers

import app.core.utils.extensions.toTime
import tmr.api.models.Weather
import tmr.impl.data.responses.WeatherResponse
import kotlin.math.roundToInt

fun WeatherResponse.toData(): Weather {
    return Weather(
        windSpeed = this.wind?.speed?.roundToInt().toString(),
        temperature = this.main?.temp?.roundToInt().toString(),
        iconCode = this.weather?.firstOrNull()?.icon.orEmpty(),
        lastUpdate = this.dt?.toTime().orEmpty()
    )
}