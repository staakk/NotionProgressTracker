package io.github.staakk.nptracker.data

import io.ktor.client.HttpClient
import io.ktor.client.engine.cio.CIO
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

internal val httpClientModule = module {
    single<HttpClient> {
        HttpClient(CIO) {

        }
    }
}

val dataModule = module {
    includes(httpClientModule)

    singleOf(::NotionService)
}