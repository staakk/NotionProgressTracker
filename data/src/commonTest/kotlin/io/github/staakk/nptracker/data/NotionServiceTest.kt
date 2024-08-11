package io.github.staakk.nptracker.data

import io.github.staakk.nptracker.data.model.JsonFilter
import io.github.staakk.nptracker.data.model.JsonQueryRequest
import io.github.staakk.nptracker.data.model.JsonSelectFilter
import io.ktor.client.HttpClient
import io.ktor.client.engine.cio.CIO
import kotlinx.coroutines.runBlocking
import kotlin.test.Test

class NotionServiceTest {

    @Test
    fun asd() {
        runBlocking {
            NotionService(httpClient(json()))
                .queryEntries(
                    request = JsonQueryRequest(
                        JsonSelectFilter(
                            "Exercise",
                            JsonSelectFilter.Equals("Dead lift")
                        )
                    ),
                    databaseId = "b93c531c-7cd4-47c4-8264-1aad5e7468cd",
                )
                .fold(
                    onSuccess = {},
                    onFailure = { it.printStackTrace() }
                )
        }
    }
}