package tmr.api.reposiroes

import tmr.api.models.UserLocation
import tmr.api.models.Weather

interface TmrRepository {

    suspend fun getWeather(
        latitude: String,
        longitude: String,
    ): Weather

    suspend fun getUserLocation(): UserLocation
}