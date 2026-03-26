package tmr.impl.usecases

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import tmr.api.models.Weather
import tmr.api.repositories.TmrRepository
import tmr.api.usecases.GetWeatherUseCase

class GetWeatherUseCaseImpl(
    private val repo: TmrRepository,
) : GetWeatherUseCase {

    override suspend fun invoke(
        latitude: String,
        longitude: String,
    ): Weather {
        return withContext(Dispatchers.IO) {
            repo.getWeather(
                longitude = longitude,
                latitude = latitude,
            )
        }
    }
}