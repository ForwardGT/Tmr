package org.example.project.core.app_settiings

import kotlinx.serialization.Serializable

@Serializable
data class AppConfig(
    val windowPositionX: Float = 11f,
    val windowPositionY: Float = 11f,
)
