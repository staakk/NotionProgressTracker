package io.github.staakk.nptracker.data.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class JsonQueryResponse(
    val results: List<JsonQueryResult>
)

@Serializable
data class JsonQueryResult(
    val id: String,
    val properties: Map<String, JsonResultProperty>
)

@Serializable
sealed class JsonResultProperty {
    abstract val id: String
    abstract val type: String

    @Serializable
    @SerialName("created_time")
    data class CreatedTime(
        override val id: String,
        override val type: String,
        @SerialName("created_time") val createdTime: String, // TODO make LocalDateTime
    ) : JsonResultProperty()

    @Serializable
    @SerialName("number")
    data class Number(
        override val id: String,
        override val type: String,
        val number: Float,
    ) : JsonResultProperty()

    @Serializable
    @SerialName("select")
    data class Select(
        override val id: String,
        override val type: String,
        val select: Value
    ) : JsonResultProperty() {

        @Serializable
        data class Value(
            val id: String,
            val name: String,
        )
    }

    @Serializable
    @SerialName("title")
    data class Title(
        override val id: String,
        override val type: String,
    ) : JsonResultProperty()

    @Serializable
    @SerialName("date")
    data class Date(
        override val id: String,
        override val type: String,
        val date: Value
    ):JsonResultProperty() {
        @Serializable
        data class Value(
            val start: String,
            val end: String?,
            @SerialName("time_zone") val timeZone: String?
        )
    }
}