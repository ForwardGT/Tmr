package app.core.di

import androidx.compose.ui.window.ApplicationScope
import app.configurations.AppControl
import app.configurations.AppControlImpl
import org.koin.core.context.GlobalContext.startKoin
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module
import tmr.api.reposiroes.TmrRepository
import tmr.api.usecases.GetWeatherUseCase
import tmr.api.usecases.SaveConfigurationsAppUseCase
import tmr.impl.repositories.TmrRepositoryImpl
import tmr.impl.usecases.GetWeatherUseCaseImpl
import tmr.impl.usecases.SaveConfigurationsAppUseCaseImpl
import tmr.impl.windows.main_window.MainStore

object TmrKoin {

    fun initKoin(applicationScope: ApplicationScope) = startKoin {
        modules(appModule(applicationScope))
    }

    private fun appModule(applicationScope: ApplicationScope) = module {
        //Other
        single { applicationScope }
        singleOf(::AppControlImpl).bind<AppControl>()

        //Usecases
        singleOf(::SaveConfigurationsAppUseCaseImpl).bind<SaveConfigurationsAppUseCase>()
        singleOf(::GetWeatherUseCaseImpl).bind<GetWeatherUseCase>()

        //ViewModels + repo
        singleOf(::MainStore)
        singleOf(::TmrRepositoryImpl).bind<TmrRepository>()
    }
}