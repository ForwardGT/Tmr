package tmr.api.usecases

import tmr.api.models.Weather

interface GetWeatherUseCase {

    suspend operator fun invoke(): Weather
}