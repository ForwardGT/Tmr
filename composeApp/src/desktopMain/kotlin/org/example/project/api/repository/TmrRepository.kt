package org.example.project.api.repository

import io.ktor.client.call.*
import io.ktor.client.request.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.example.project.api.mappers.toData
import org.example.project.api.data.models.Weather
import org.example.project.api.data.network.KtorClient
import org.example.project.api.data.responses.WeatherResponse
import org.example.project.core.utils.constant.Constants.API_KEY

interface TmrRepository {
    suspend fun getWeather(): Weather
}

class TmrRepositoryImpl : TmrRepository {

    override suspend fun getWeather(): Weather {
        return withContext(Dispatchers.IO) {
            KtorClient.client
                .get("weather?lat=56.236&lon=41.141&appid=$API_KEY&units=metric")
                .body<WeatherResponse>()
                .toData()
        }
    }
}

