package app.core.di

import org.koin.core.context.GlobalContext.startKoin
import org.koin.core.module.dsl.singleOf
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.bind
import org.koin.dsl.module
import tmr.api.reposiroes.TmrRepository
import tmr.api.usecases.GetWeatherUseCase
import tmr.api.usecases.SaveConfigurationsAppUseCase
import tmr.impl.repositories.TmrRepositoryImpl
import tmr.impl.usecases.GetWeatherUseCaseImpl
import tmr.impl.usecases.SaveConfigurationsAppUseCaseImpl
import tmr.impl.windows.main_window.TmrStore

object TmrKoin {

    fun initKoin() = startKoin { modules(appModule) }

    private val appModule = module {
        singleOf(::SaveConfigurationsAppUseCaseImpl).bind<SaveConfigurationsAppUseCase>()
        singleOf(::GetWeatherUseCaseImpl).bind<GetWeatherUseCase>()

        viewModelOf(::TmrStore)
        singleOf(::TmrRepositoryImpl).bind<TmrRepository>()
    }
}