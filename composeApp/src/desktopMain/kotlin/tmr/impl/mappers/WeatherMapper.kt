package tmr.impl.mappers

import app.core.utils.constant.Constants.BASE_URL_IMAGE
import app.core.utils.extensions.toShortTime
import tmr.api.models.Weather
import tmr.impl.data.responses.WeatherResponse
import kotlin.math.roundToInt

fun WeatherResponse.toData(): Weather {

    val iconCode = this.weather?.firstOrNull()?.icon.orEmpty()

    return Weather(
        windSpeed = this.wind?.speed?.roundToInt().toString(),
        temperature = this.main?.temp?.roundToInt().toString(),
        iconCode = iconCode,
        lastUpdate = this.dt?.toShortTime().orEmpty(),
        iconUrl = "$BASE_URL_IMAGE/${iconCode}.png",
    )
}