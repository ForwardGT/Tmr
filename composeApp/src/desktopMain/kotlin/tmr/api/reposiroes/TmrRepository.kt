package tmr.api.reposiroes

import tmr.api.models.Weather

interface TmrRepository {

    suspend fun getWeather(): Weather
}