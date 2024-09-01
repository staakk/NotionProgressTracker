package io.github.staakk.nptracker.framework.navigation

import androidx.core.bundle.Bundle
import androidx.navigation.NavType
import io.github.staakk.nptracker.domain.EntryId
import kotlin.reflect.typeOf

object EntryIdNavType : NavType<EntryId?>(true) {
    val typeMap = typeOf<EntryId?>() to this

    override fun get(bundle: Bundle, key: String): EntryId? =
        bundle.getString(key)
            ?.let { value -> value.takeIf { it.isNotBlank() } }
            ?.let { EntryId(it) }

    override fun parseValue(value: String): EntryId = EntryId(value)

    override fun put(bundle: Bundle, key: String, value: EntryId?) {
        bundle.putString(key, value?.value)
    }
}