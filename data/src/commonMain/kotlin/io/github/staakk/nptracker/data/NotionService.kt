package io.github.staakk.nptracker.data

import io.github.staakk.nptracker.Secrets
import io.ktor.client.HttpClient
import io.ktor.client.request.get
import io.ktor.client.request.headers
import io.ktor.client.statement.bodyAsText

class NotionService(val httpClient: HttpClient) {

    suspend fun getDatabase() {
        httpClient
            .get("https://api.notion.com/v1/databases/b93c531c-7cd4-47c4-8264-1aad5e7468cd") {
                headers {
                    append(
                        "Authorization",
                        "Bearer ${Secrets.notionApiSecret}",
                    )
                    append(
                        "Notion-Version",
                        "2022-06-28",
                    )
                }
            }
            .let {
                println(it.bodyAsText())
            }
    }
}