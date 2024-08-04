package io.github.staakk.nptracker.data

import io.ktor.client.HttpClient
import io.ktor.client.engine.cio.CIO
import kotlinx.coroutines.runBlocking
import kotlin.test.Test

class NotionServiceTest {

    @Test
    fun asd() {
        runBlocking {
            NotionService(httpClient(json())).getDatabase()
        }
    }
}