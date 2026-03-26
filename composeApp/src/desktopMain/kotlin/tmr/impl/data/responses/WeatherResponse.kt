package tmr.impl.data.responses

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class WeatherResponse(
    @SerialName("current")
    val current: CurrentWeather? = null,
)

@Serializable
data class CurrentWeather(
    @SerialName("time")
    val time: String? = null,
    @SerialName("temperature_2m")
    val temperature2m: Double? = null,
    @SerialName("wind_speed_10m")
    val windSpeed10m: Double? = null,
    @SerialName("weather_code")
    val weatherCode: Int? = null,
    @SerialName("is_day")
    val isDay: Int? = null,
)
