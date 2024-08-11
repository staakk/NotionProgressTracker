package io.github.staakk.nptracker.editentry

import io.github.staakk.nptracker.domain.Entry

internal data class EntryScreenState(
    val isLoading: Boolean = false,
    val entry: Entry = Entry(),
    val availableExercises: List<String> = emptyList(),
    val lastEntries: List<Entry> = emptyList(),
)