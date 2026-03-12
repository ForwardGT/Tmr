package tmr.impl.data.responses


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class UserLocationResponse(
    @SerialName("borders")
    val borders: String? = null,
    @SerialName("calling_code")
    val callingCode: String? = null,
    @SerialName("capital")
    val capital: String? = null,
    @SerialName("city")
    val city: String? = null,
    @SerialName("connection")
    val connection: Connection? = null,
    @SerialName("continent")
    val continent: String? = null,
    @SerialName("continent_code")
    val continentCode: String? = null,
    @SerialName("country")
    val country: String? = null,
    @SerialName("country_code")
    val countryCode: String? = null,
    @SerialName("flag")
    val flag: Flag? = null,
    @SerialName("ip")
    val ip: String? = null,
    @SerialName("is_eu")
    val isEu: Boolean? = null,
    @SerialName("latitude")
    val latitude: Double? = null,
    @SerialName("longitude")
    val longitude: Double? = null,
    @SerialName("postal")
    val postal: String? = null,
    @SerialName("readme")
    val readme: String? = null,
    @SerialName("region")
    val region: String? = null,
    @SerialName("region_code")
    val regionCode: String? = null,
    @SerialName("success")
    val success: Boolean? = null,
    @SerialName("timezone")
    val timezone: Timezone? = null,
    @SerialName("type")
    val type: String? = null
) {
    @Serializable
    data class Connection(
        @SerialName("asn")
        val asn: Int? = null,
        @SerialName("domain")
        val domain: String? = null,
        @SerialName("isp")
        val isp: String? = null,
        @SerialName("org")
        val org: String? = null
    )

    @Serializable
    data class Flag(
        @SerialName("emoji")
        val emoji: String? = null,
        @SerialName("emoji_unicode")
        val emojiUnicode: String? = null,
        @SerialName("img")
        val img: String? = null
    )

    @Serializable
    data class Timezone(
        @SerialName("abbr")
        val abbr: String? = null,
        @SerialName("id")
        val id: String? = null,
        @SerialName("is_dst")
        val isDst: Boolean? = null,
        @SerialName("offset")
        val offset: Int? = null,
        @SerialName("utc")
        val utc: String? = null
    )
}