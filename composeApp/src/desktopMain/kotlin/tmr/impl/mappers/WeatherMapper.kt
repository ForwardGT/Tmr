package tmr.impl.mappers

import app.core.utils.constant.Constants.BASE_URL_IMAGE
import app.core.utils.extensions.toShortTimeFromIso
import tmr.api.models.Weather
import tmr.impl.data.responses.WeatherResponse
import kotlin.math.roundToInt

fun WeatherResponse.toData(): Weather {
    val current = current
    val iconCode = mapWmoToOpenWeatherIconCode(
        weatherCode = current?.weatherCode,
        isDay = current?.isDay == 1,
    )

    return Weather(
        windSpeed = current?.windSpeed10m.toRoundedString(),
        temperature = current?.temperature2m.toRoundedString(),
        iconCode = iconCode,
        lastUpdate = current?.time.toShortTimeFromIso(),
        iconUrl = iconCode.toIconUrl(),
    )
}

private fun mapWmoToOpenWeatherIconCode(
    weatherCode: Int?,
    isDay: Boolean,
): String {
    val suffix = if (isDay) "d" else "n"

    return when (weatherCode) {
        0 -> "01$suffix"
        1 -> "02$suffix"
        2 -> "03$suffix"
        3 -> "04$suffix"

        45, 48 -> "50$suffix"

        51, 53, 55, 56, 57,
        80, 81, 82 -> "09$suffix"

        61, 63, 65, 66, 67 -> "10$suffix"

        71, 73, 75, 77, 85, 86 -> "13$suffix"

        95, 96, 99 -> "11$suffix"

        else -> "03$suffix"
    }
}

private fun Double?.toRoundedString(): String =
    this?.roundToInt()?.toString().orEmpty()

private fun String.toIconUrl(): String =
    "$BASE_URL_IMAGE/$this@2x.png"
