package io.github.staakk.nptracker.data.model

import kotlinx.serialization.Serializable

@Serializable
data class JsonDatabase(
    val id: String,
    val properties: Map<String, JsonColumnProperty>
)