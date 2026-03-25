package app.application.configurations

import app.core.utils.extensions.reduce
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.withContext
import kotlinx.serialization.json.Json
import java.io.File
import java.nio.file.Paths

interface ConfigManager {

    val appConfig: StateFlow<AppConfig>

    suspend fun saveConfig(transform: AppConfig.() -> AppConfig)
}

class ConfigManagerImpl : ConfigManager {

    private val pathInstallApp = Paths.get("").toAbsolutePath().toString()
    private val configFile = File(pathInstallApp, "app_config.json")

    private val _appConfig = MutableStateFlow(loadConfig())
    override val appConfig: StateFlow<AppConfig> = _appConfig.asStateFlow()

    override suspend fun saveConfig(transform: AppConfig.() -> AppConfig) {
        val newConfig = _appConfig.value.transform()

        withContext(Dispatchers.IO) {
            configFile.writeText(Json.encodeToString(newConfig))
            println("Json config file saved to path: $configFile")
        }

        withContext(Dispatchers.Main) {
            _appConfig.reduce { newConfig }
        }
    }

    private fun loadConfig(): AppConfig {
        return if (configFile.exists())
            runCatching {
                Json.decodeFromString<AppConfig>(configFile.readText())
            }.getOrElse { AppConfig() }
        else AppConfig()
    }
}
