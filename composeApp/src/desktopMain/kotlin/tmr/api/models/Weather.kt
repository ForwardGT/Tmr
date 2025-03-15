package tmr.api.models

import kotlinx.serialization.Serializable

@Serializable
data class Weather(
    val temperature: String = "",
    val iconCode: String = "",
    val windSpeed: String = "",
    val lastUpdate: String = "",
)
