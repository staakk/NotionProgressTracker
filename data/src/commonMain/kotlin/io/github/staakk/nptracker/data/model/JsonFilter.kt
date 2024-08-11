package io.github.staakk.nptracker.data.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class JsonQueryRequest(
    val filter: JsonFilter? = null,
    @SerialName("page_size") val pageSize: Int = 10
)

@Serializable
sealed class JsonFilter {
    abstract val property: String
}

@Serializable
@SerialName("select")
data class JsonSelectFilter(
    override val property: String,
    val select: Value,
) : JsonFilter() {

    @Serializable
    sealed interface Value

    @Serializable
    data class Equals(val equals: String): Value
}