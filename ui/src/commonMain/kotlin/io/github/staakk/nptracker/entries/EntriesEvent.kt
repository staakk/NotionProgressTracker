package io.github.staakk.nptracker.entries

import io.github.staakk.nptracker.domain.Entry

sealed class EntriesEvent {
    data object ScreenLaunched: EntriesEvent()
    data object AddNew: EntriesEvent()
    data class EditEntry(val selected: Entry): EntriesEvent()
}