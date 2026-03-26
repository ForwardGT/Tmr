package app.application.configurations

import kotlinx.serialization.Serializable

@Serializable
data class AppConfig(
    val windowPositionX: Float = 11f,
    val windowPositionY: Float = 11f,
    val alwaysOnTop: Boolean = true,
    val notificationsEnabled: Boolean = false,
    val defaultShutdownMinutes: Int = 60,
)
