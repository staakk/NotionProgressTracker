package io.github.staakk.nptracker.data

import io.github.staakk.nptracker.data.model.JsonDatabase
import io.github.staakk.nptracker.data.model.JsonQueryRequest
import io.github.staakk.nptracker.data.model.JsonQueryResponse
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.post
import io.ktor.client.request.setBody

class NotionService(private val httpClient: HttpClient) {

    suspend fun getDatabase(databaseId: String) : Result<JsonDatabase> = runCatching {
        httpClient
            .get(databasePath(databaseId))
            .body<JsonDatabase>()
    }

    suspend fun queryEntries(request: JsonQueryRequest, databaseId: String) : Result<JsonQueryResponse> = runCatching {
        httpClient
            .post("${databasePath(databaseId)}/query") {
                setBody(request)
            }
            .body<JsonQueryResponse>()
    }

    private fun databasePath(databaseId: String) = "v1/databases/$databaseId"
}