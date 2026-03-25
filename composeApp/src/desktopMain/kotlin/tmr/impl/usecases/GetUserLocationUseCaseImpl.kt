package tmr.impl.usecases

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import tmr.api.models.UserLocation
import tmr.api.repositories.TmrRepository
import tmr.api.usecases.GetUserLocationUseCase

class GetUserLocationUseCaseImpl(
    private val repo: TmrRepository,
) : GetUserLocationUseCase {

    override suspend fun invoke(): UserLocation {
        return withContext(Dispatchers.IO) {
            repo.getUserLocation()
        }
    }
}