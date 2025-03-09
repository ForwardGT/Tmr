package org.example.project.api.data.models

import kotlinx.serialization.Serializable

@Serializable
data class Weather(
    val temperature: String = "",
    val iconCode: String = "",
    val windSpeed: String = "",
)
