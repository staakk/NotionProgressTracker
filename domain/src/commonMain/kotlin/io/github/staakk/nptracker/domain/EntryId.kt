package io.github.staakk.nptracker.domain

import kotlinx.serialization.Serializable
import kotlin.jvm.JvmInline

@JvmInline
@Serializable
value class EntryId(
    @Suppress("MemberVisibilityCanBePrivate")
    val value: String
) {
    init {
        check(value.isNotBlank()) { "Cannot create EntryId, `id` cannot be blank."}
    }
}