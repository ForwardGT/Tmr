package tmr.impl.repositories

import app.core.network.KtorClient
import app.core.utils.constant.Constants.API_KEY
import io.ktor.client.call.*
import io.ktor.client.request.*
import tmr.api.models.Weather
import tmr.api.reposiroes.TmrRepository
import tmr.impl.data.responses.WeatherResponse
import tmr.impl.mappers.toData

class TmrRepositoryImpl : TmrRepository {

    override suspend fun getWeather(): Weather {
        return KtorClient.client
            .get("weather?lat=56.236&lon=41.141&appid=$API_KEY&units=metric")
            .body<WeatherResponse>()
            .toData()
    }
}