package tmr.impl.repositories

import app.core.network.KtorClient
import app.core.utils.constant.Constants.API_KEY
import io.ktor.client.call.*
import io.ktor.client.request.*
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
            .get("weather?lat=$latitude&lon=$longitude&appid=$API_KEY&units=metric")
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