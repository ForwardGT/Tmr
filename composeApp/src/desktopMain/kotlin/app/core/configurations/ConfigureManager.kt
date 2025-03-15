package app.core.configurations

import kotlinx.serialization.json.Json
import java.io.File
import java.nio.file.Paths

object ConfigManager {
    private val configFile = File(getExeDirectory(), "app_config.json")

    fun loadConfig(): AppConfig {
        return if (configFile.exists()) {
            runCatching {
                Json.decodeFromString<AppConfig>(configFile.readText())
            }.getOrElse { AppConfig() }
        } else {
            AppConfig()
        }
    }

    fun saveConfig(config: AppConfig) {
        configFile.writeText(Json.encodeToString(config))
    }
}

fun getExeDirectory(): File {
    val path = Paths.get("").toAbsolutePath().toString()
    return File(path)
}