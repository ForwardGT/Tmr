package app.core.configurations

import kotlinx.serialization.Serializable

@Serializable
data class AppConfig(
    val windowPositionX: Float = 11f,
    val windowPositionY: Float = 11f,
)
