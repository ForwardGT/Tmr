package tmr.impl.mappers

import tmr.api.models.UserLocation
import tmr.impl.data.responses.UserLocationResponse

fun UserLocationResponse.toData(): UserLocation {

    return UserLocation(
        country = country.orEmpty(),
        region = region.orEmpty(),
        city = city.orEmpty(),
        latitude = latitude.toString(),
        longitude = longitude.toString(),
        flagImgLink = flag?.img.orEmpty(),
        ip = ip.orEmpty(),
    )
}
