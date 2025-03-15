package app.core

import org.koin.core.context.GlobalContext.startKoin
import org.koin.core.module.dsl.singleOf
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.bind
import org.koin.dsl.module
import tmr.api.reposiroes.TmrRepository
import tmr.impl.repositories.TmrRepositoryImpl
import tmr.impl.windows.main_window.TmrStore

object TmrKoin {

    fun initKoin() = startKoin {
        modules(appModule)
    }

    private val appModule = module {
        viewModelOf(::TmrStore)
        singleOf(::TmrRepositoryImpl).bind<TmrRepository>()
    }
}