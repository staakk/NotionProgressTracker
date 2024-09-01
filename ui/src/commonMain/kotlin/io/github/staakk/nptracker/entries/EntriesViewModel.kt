package io.github.staakk.nptracker.entries

import io.github.staakk.nptracker.domain.usecase.GetLatestEntries
import io.github.staakk.nptracker.framework.MviViewModel

internal class EntriesViewModel(
    private val getLatestEntries: GetLatestEntries,
    private val navigator: EntriesScreenNavigator,
) : MviViewModel<EntriesState, EntriesEvent>(EntriesState()) {

    init {
        dispatch(EntriesEvent.ScreenLaunched)
    }

    override suspend fun handleEvent(event: EntriesEvent) = when (event) {
        EntriesEvent.ScreenLaunched -> transform {
            it.copy(entries = getLatestEntries().getOrDefault(emptyList()))
        }
        EntriesEvent.AddNew -> consume {
            navigator.onAddEntry()
        }
        is EntriesEvent.EditEntry -> consume {
            navigator.onEditEntry(event.selected.id)
        }
    }
}