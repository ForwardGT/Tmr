package tmr.impl.usecases

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import tmr.api.usecases.SaveConfigurationsAppUseCase

class SaveConfigurationsAppUseCaseImpl : SaveConfigurationsAppUseCase {
    override suspend fun invoke() {

        withContext(Dispatchers.IO) {

        }
    }
}