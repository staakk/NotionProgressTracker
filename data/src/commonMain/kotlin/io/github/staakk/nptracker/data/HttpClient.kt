package io.github.staakk.nptracker.data

import io.github.staakk.nptracker.Secrets
import io.ktor.client.HttpClient
import io.ktor.client.HttpClientConfig
import io.ktor.client.engine.cio.CIO
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.defaultRequest
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logger
import io.ktor.client.plugins.logging.Logging
import io.ktor.client.plugins.logging.SIMPLE
import io.ktor.client.request.bearerAuth
import io.ktor.client.request.header
import io.ktor.http.ContentType
import io.ktor.http.HttpHeaders
import io.ktor.http.URLProtocol
import io.ktor.http.contentType
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json

internal fun httpClient(json: Json): HttpClient = HttpClient(CIO) {
    notionDefaultRequest()
    logging()
    install(ContentNegotiation) {
        json(json)
    }
}

fun HttpClientConfig<*>.notionDefaultRequest() = defaultRequest {
    url {
        protocol = URLProtocol.HTTPS
        host = "api.notion.com"
    }

    bearerAuth(Secrets.notionApiSecret)
    contentType(ContentType.Application.Json)
    header("Notion-Version", "2022-06-28")
}

fun HttpClientConfig<*>.logging() = install(Logging) {
    sanitizeHeader { header -> header == HttpHeaders.Authorization }
    logger = Logger.SIMPLE
    level = LogLevel.ALL
}

