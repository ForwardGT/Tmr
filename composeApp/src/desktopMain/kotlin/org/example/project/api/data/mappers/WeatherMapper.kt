package org.example.project.api.data.mappers

import org.example.project.api.data.models.Weather
import org.example.project.api.data.responses.WeatherResponse
import java.time.Instant
import java.time.ZoneId
import java.time.format.DateTimeFormatter

fun WeatherResponse.toData(): Weather {
    return Weather(
        windSpeed = this.wind?.speed?.toInt().toString(),
        temperature = this.main?.temp?.toInt().toString(),
        iconCode = this.weather?.firstOrNull()?.icon.orEmpty(),
        lastUpdate = this.dt?.toTime().orEmpty()
    )
}

private fun Long.toTime(): String {
    return DateTimeFormatter.ofPattern("HH:mm:ss")
        .withZone(ZoneId.systemDefault())
        .format(Instant.ofEpochSecond(this))
}