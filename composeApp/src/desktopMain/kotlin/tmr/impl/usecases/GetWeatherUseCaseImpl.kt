package tmr.impl.usecases

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import tmr.api.models.Weather
import tmr.api.reposiroes.TmrRepository
import tmr.api.usecases.GetWeatherUseCase

class GetWeatherUseCaseImpl(
    private val repo: TmrRepository,
) : GetWeatherUseCase {

    override suspend fun invoke(): Weather {
        return withContext(Dispatchers.IO) {
            repo.getWeather()
        }
    }
}