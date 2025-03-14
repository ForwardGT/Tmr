package org.example.project

import org.example.project.api.repository.TmrRepository
import org.example.project.api.repository.TmrRepositoryImpl
import org.koin.core.context.GlobalContext.startKoin
import org.koin.core.module.dsl.singleOf
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.bind
import org.koin.dsl.module

object TmrKoin {

    fun initKoin() = startKoin {
        modules(appModule)
    }

    private val appModule = module {
        viewModelOf(::TmrStore)
        singleOf(::TmrRepositoryImpl).bind<TmrRepository>()
    }
}