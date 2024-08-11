package io.github.staakk.nptracker.entries

import io.github.staakk.nptracker.domain.Repetitions
import io.github.staakk.nptracker.domain.Weight
import io.github.staakk.nptracker.domain.Entry
import io.github.staakk.nptracker.domain.usecase.GetLatestEntries
import io.github.staakk.nptracker.framework.MviViewModel
import kotlinx.datetime.LocalDateTime

internal class EntriesViewModel(
    private val getLatestEntries: GetLatestEntries,
) : MviViewModel<EntriesState, EntriesEvent>(EntriesState()) {

    init {
        dispatch(EntriesEvent.ScreenLaunched)
    }

    override suspend fun handleEvent(event: EntriesEvent) = when (event) {
        EntriesEvent.ScreenLaunched -> action {
            it.copy(entries = getLatestEntries().getOrDefault(emptyList()))
        }
    }
}