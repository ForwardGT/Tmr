package tmr.api.models

data class UserLocation(
    val country: String = "",
    val region: String = "",
    val city: String = "",
    val latitude: String = "",
    val longitude: String = "",
    val flagImgLink: String = "",
    val ip: String = "",
)
