package app.application.configurations

import app.core.utils.extensions.reduce
import app.core.utils.log.TmrLogger
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

    companion object {
        private const val TAG = "ConfigManager"
    }

    private val pathInstallApp = Paths.get("").toAbsolutePath().toString()
    private val configFile = File(pathInstallApp, "app_config.json")

    private val _appConfig = MutableStateFlow(loadConfig())
    override val appConfig: StateFlow<AppConfig> = _appConfig.asStateFlow()

    override suspend fun saveConfig(transform: AppConfig.() -> AppConfig) {
        val newConfig = _appConfig.value.transform()

        val saveSuccess = withContext(Dispatchers.IO) {
            runCatching {
                configFile.writeText(Json.encodeToString(newConfig))
            }
                .onSuccess {
                    TmrLogger.d(TAG, "Config saved to path: $configFile")
                }
                .onFailure { e ->
                    TmrLogger.e(TAG, e, "Failed to save config")
                }
                .isSuccess
        }

        if (!saveSuccess) return

        withContext(Dispatchers.Main) {
            _appConfig.reduce { newConfig }
        }
    }

    private fun loadConfig(): AppConfig {
        return if (configFile.exists())
            runCatching {
                Json.decodeFromString<AppConfig>(configFile.readText())
            }
                .onFailure { e ->
                    TmrLogger.e(TAG, e, "Failed to read config")
                }
                .getOrElse { AppConfig() }
        else AppConfig()
    }
}
