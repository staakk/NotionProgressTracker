package io.github.staakk.nptracker.entries

import io.github.staakk.nptracker.domain.usecase.GetLatestEntries
import io.github.staakk.nptracker.framework.MviViewModel

internal class EntriesViewModel(
    private val getLatestEntries: GetLatestEntries,
) : MviViewModel<EntriesState, EntriesEvent>(EntriesState()) {

    init {
        dispatch(EntriesEvent.ScreenLaunched)
    }

    override suspend fun handleEvent(event: EntriesEvent) = when (event) {
        EntriesEvent.ScreenLaunched -> transform {
            it.copy(entries = getLatestEntries().getOrDefault(emptyList()))
        }
        EntriesEvent.AddNew -> consume {
        }
        is EntriesEvent.EditEntry -> consume {
            val (selected) = event
        }
    }
}