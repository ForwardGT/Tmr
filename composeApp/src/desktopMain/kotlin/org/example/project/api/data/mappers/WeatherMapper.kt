package org.example.project.api.data.mappers

import org.example.project.api.data.models.Weather
import org.example.project.api.data.responses.WeatherResponse

fun WeatherResponse.toData(): Weather {
    return Weather(
        windSpeed = this.wind?.speed?.toInt().toString(),
        temperature = this.main?.temp?.toInt().toString(),
        iconCode = this.weather?.firstOrNull()?.icon.orEmpty(),
    )
}