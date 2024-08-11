package io.github.staakk.nptracker.data.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
sealed class JsonColumnProperty {
    abstract val id: String
    abstract val name: String

    @Serializable
    @SerialName("select")
    data class Select(
        override val id: String,
        override val name: String,
        val select: SelectProperties,
    ) : JsonColumnProperty() {

        @Serializable
        data class SelectProperties(
            val options: List<Option>
        )

        @Serializable
        data class Option(
            val id: String,
            val name: String,
        )
    }

    @Serializable
    @SerialName("created_time")
    data class CreatedTime(override val id: String, override val name: String): JsonColumnProperty()

    @Serializable
    @SerialName("number")
    data class Number(override val id: String, override val name: String): JsonColumnProperty()

    @Serializable
    @SerialName("date")
    data class Date(override val id: String, override val name: String): JsonColumnProperty()

    @Serializable
    @SerialName("title")
    data class Title(override val id: String, override val name: String): JsonColumnProperty()
}
