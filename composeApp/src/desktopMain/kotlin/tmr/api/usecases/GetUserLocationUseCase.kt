package tmr.api.usecases

import tmr.api.models.UserLocation

interface GetUserLocationUseCase {

    suspend operator fun invoke(): UserLocation
}