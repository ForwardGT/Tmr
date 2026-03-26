package app.core.di

import androidx.compose.ui.window.ApplicationScope
import app.application.configurations.ConfigManager
import app.application.configurations.ConfigManagerImpl
import app.core.window.WindowManager
import org.koin.core.KoinApplication
import org.koin.core.module.dsl.singleOf
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.bind
import org.koin.dsl.module
import tmr.api.repositories.TmrRepository
import tmr.api.usecases.GetUserLocationUseCase
import tmr.api.usecases.GetWeatherUseCase
import tmr.impl.repositories.TmrRepositoryImpl
import tmr.impl.usecases.GetUserLocationUseCaseImpl
import tmr.impl.usecases.GetWeatherUseCaseImpl
import tmr.impl.windows.setting_window.SettingsStore
import tmr.impl.windows.timer_window.TimerStore

object TmrKoin {

    fun KoinApplication.initKoin(applicationScope: ApplicationScope) {
        modules(appModule(applicationScope))
    }

    private fun appModule(applicationScope: ApplicationScope) = module {
        //Other
        single { applicationScope }
        singleOf(::WindowManager)
        singleOf(::ConfigManagerImpl).bind<ConfigManager>()

        //Usecases
        singleOf(::GetWeatherUseCaseImpl).bind<GetWeatherUseCase>()
        singleOf(::GetUserLocationUseCaseImpl).bind<GetUserLocationUseCase>()

        //ViewModels + repo
        viewModelOf(::TimerStore)
        viewModelOf(::SettingsStore)
        singleOf(::TmrRepositoryImpl).bind<TmrRepository>()
    }
}
