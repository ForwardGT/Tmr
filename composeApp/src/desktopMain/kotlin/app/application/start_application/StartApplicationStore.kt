package app.application.start_application

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import app.application.configurations.ConfigManager
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class StartApplicationStore : ViewModel(), KoinComponent {

    private val appConfigManager: ConfigManager by inject()

    init {
        viewModelScope.launch(Dispatchers.Default) {
            appConfigManager.appConfig.collect {

            }
        }
    }


}