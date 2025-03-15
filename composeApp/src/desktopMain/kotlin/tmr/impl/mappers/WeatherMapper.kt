package tmr.impl.mappers

import tmr.api.models.Weather
import tmr.impl.data.responses.WeatherResponse
import java.time.Instant
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import kotlin.math.roundToInt

fun WeatherResponse.toData(): Weather {
    return Weather(
        windSpeed = this.wind?.speed?.roundToInt().toString(),
        temperature = this.main?.temp?.roundToInt().toString(),
        iconCode = this.weather?.firstOrNull()?.icon.orEmpty(),
        lastUpdate = this.dt?.toTime().orEmpty()
    )
}

private fun Long.toTime(): String {
    return DateTimeFormatter.ofPattern("HH:mm:ss")
        .withZone(ZoneId.systemDefault())
        .format(Instant.ofEpochSecond(this))
}