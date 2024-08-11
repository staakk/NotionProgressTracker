package io.github.staakk.nptracker.entries

sealed class EntriesEvent {
    data object ScreenLaunched: EntriesEvent()
}