package app.core.di

import androidx.compose.ui.window.ApplicationScope
import app.application.configurations.AppControl
import app.application.configurations.AppControlImpl
import app.application.configurations.ConfigManager
import app.application.configurations.ConfigManagerImpl
import app.application.start_application.StartApplicationStore
import org.koin.core.context.GlobalContext.startKoin
import org.koin.core.module.dsl.singleOf
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.bind
import org.koin.dsl.module
import tmr.api.reposiroes.TmrRepository
import tmr.api.usecases.GetUserLocationUseCase
import tmr.api.usecases.GetWeatherUseCase
import tmr.api.usecases.SaveConfigurationsAppUseCase
import tmr.impl.repositories.TmrRepositoryImpl
import tmr.impl.usecases.GetUserLocationUseCaseImpl
import tmr.impl.usecases.GetWeatherUseCaseImpl
import tmr.impl.usecases.SaveConfigurationsAppUseCaseImpl
import tmr.impl.windows.main_window.TimerStore

object TmrKoin {

    fun initKoin(applicationScope: ApplicationScope) = startKoin {
        modules(appModule(applicationScope))
    }

    private fun appModule(applicationScope: ApplicationScope) = module {
        //Other
        single { applicationScope }
        singleOf(::AppControlImpl).bind<AppControl>()
        singleOf(::ConfigManagerImpl).bind<ConfigManager>()

        //Usecases
        singleOf(::SaveConfigurationsAppUseCaseImpl).bind<SaveConfigurationsAppUseCase>()
        singleOf(::GetWeatherUseCaseImpl).bind<GetWeatherUseCase>()
        singleOf(::GetUserLocationUseCaseImpl).bind<GetUserLocationUseCase>()

        //ViewModels + repo
        viewModelOf(::TimerStore)
        singleOf(::TmrRepositoryImpl).bind<TmrRepository>()

        viewModelOf(::StartApplicationStore)
    }
}