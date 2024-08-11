package io.github.staakk.nptracker.entries

import io.github.staakk.nptracker.domain.Entry

data class EntriesState(
    val entries: List<Entry> = emptyList()
)