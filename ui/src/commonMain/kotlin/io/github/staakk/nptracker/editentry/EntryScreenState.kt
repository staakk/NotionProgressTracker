package io.github.staakk.nptracker.editentry

import io.github.staakk.nptracker.domain.Entry
import io.github.staakk.nptracker.domain.EntryId

internal data class EntryScreenState(
    val isLoading: Boolean = false,
    val entry: Entry = Entry(EntryId("fake_id")),
    val availableExercises: List<String> = emptyList(),
    val lastEntries: List<Entry> = emptyList(),
)