package org.example.project

import org.example.project.api.data.repository.TmrRepository
import org.example.project.api.data.repository.TmrRepositoryImpl
import org.example.project.core.store.TmrStore
import org.koin.core.context.GlobalContext.startKoin
import org.koin.core.module.dsl.singleOf
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.bind
import org.koin.dsl.module

object KoinTmr {

    fun initKoin() = startKoin {
        modules(appModule)
    }

    private val appModule = module {
        viewModelOf(::TmrStore)
        singleOf(::TmrRepositoryImpl).bind<TmrRepository>()
    }
}