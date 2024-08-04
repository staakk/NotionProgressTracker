package io.github.staakk.nptracker.data

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class JsonDatabase(
    val id: String,
    val properties: Map<String, Property>
)

@Serializable
sealed class Property {
    abstract val id: String
    abstract val name: String

    @Serializable
    @SerialName("select")
    data class Select(
        override val id: String,
        override val name: String,
        val select: SelectProperties,
    ) : Property() {

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
    data class CreatedTime(override val id: String, override val name: String): Property()

    @Serializable
    @SerialName("number")
    data class Number(override val id: String, override val name: String): Property()

    @Serializable
    @SerialName("date")
    data class Date(override val id: String, override val name: String): Property()

    @Serializable
    @SerialName("title")
    data class Title(override val id: String, override val name: String): Property()
}
