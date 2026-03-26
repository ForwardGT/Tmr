package tmr.impl.repositories

import app.core.network.KtorClient
import app.core.utils.constant.Constants.WEATHER_FORECAST_PATH
import io.ktor.client.call.*
import io.ktor.client.request.*
import io.ktor.http.*
import tmr.api.models.UserLocation
import tmr.api.models.Weather
import tmr.api.repositories.TmrRepository
import tmr.impl.data.responses.UserLocationResponse
import tmr.impl.data.responses.WeatherResponse
import tmr.impl.mappers.toData

class TmrRepositoryImpl : TmrRepository {

    override suspend fun getWeather(
        latitude: String,
        longitude: String,
    ): Weather {
        return KtorClient.client
            .get {
                url {
                    encodedPath = WEATHER_FORECAST_PATH
                    parameters.append("latitude", latitude)
                    parameters.append("longitude", longitude)
                    parameters.append("current", "temperature_2m,wind_speed_10m,weather_code,is_day")
                    parameters.append("wind_speed_unit", "ms")
                    parameters.append("timezone", "auto")
                }
            }
            .body<WeatherResponse>()
            .toData()
    }

    override suspend fun getUserLocation(): UserLocation {
        return KtorClient.client
            .get("https://ipwho.is/")
            .body<UserLocationResponse>()
            .toData()
    }
}
