package io.github.staakk.nptracker.data

import kotlinx.serialization.json.Json

internal fun json() = Json {
    prettyPrint = true
    isLenient = true
    ignoreUnknownKeys = true
    encodeDefaults = true
    explicitNulls = false
}