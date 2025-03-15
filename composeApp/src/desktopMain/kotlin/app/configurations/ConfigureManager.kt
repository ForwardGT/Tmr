package app.configurations

import androidx.compose.ui.window.WindowPosition
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kotlinx.serialization.json.Json
import java.io.File
import java.nio.file.Paths

object ConfigManager {
    private val configFile = File(getExeDirectory(), "app_config.json")

    fun loadConfig(): AppConfig {
        return if (configFile.exists())
            runCatching { Json.decodeFromString<AppConfig>(configFile.readText()) }
                .getOrElse { AppConfig() }
        else AppConfig()
    }

    suspend fun saveConfig(windowPosition: WindowPosition) {

        val config = AppConfig(
            windowPositionY = windowPosition.y.value,
            windowPositionX = windowPosition.x.value,
        )

        withContext(Dispatchers.IO) {
            print("Json config file saved to path: $configFile ")
            configFile.writeText(Json.encodeToString(config))
        }
    }
}

private fun getExeDirectory() = Paths.get("").toAbsolutePath().toString()
