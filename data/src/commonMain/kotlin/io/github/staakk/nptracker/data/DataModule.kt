package io.github.staakk.nptracker.data

import io.github.staakk.nptracker.domain.WorkoutsDataSource
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

val dataModule = module {
    singleOf(::json)
    singleOf(::httpClient)
    singleOf(::NotionService)
    single<WorkoutsDataSource> { WorkoutsRemoteDataSource(get()) }
}