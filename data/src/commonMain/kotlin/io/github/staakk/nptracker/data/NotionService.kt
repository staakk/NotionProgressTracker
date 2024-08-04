package io.github.staakk.nptracker.data

import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.headers
import io.ktor.client.statement.bodyAsText

class NotionService(private val httpClient: HttpClient) {

    suspend fun getDatabase(databaseId: String) : Result<JsonDatabase> = runCatching {
        httpClient
            .get("v1/databases/$databaseId")
            .body<JsonDatabase>()
    }
}