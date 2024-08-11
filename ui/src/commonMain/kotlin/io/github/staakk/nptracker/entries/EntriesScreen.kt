package io.github.staakk.nptracker.entries

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import io.github.staakk.nptracker.Dimens
import io.github.staakk.nptracker.shared.EntryCompact
import org.koin.compose.currentKoinScope

@Composable
fun EntriesScreen() {
    val koinScope = currentKoinScope()
    val viewModel = viewModel { koinScope.get<EntriesViewModel>() }
    val state by viewModel.state.collectAsState()
    ScreenContent(state, viewModel::dispatch)
}

@Composable
private fun ScreenContent(state: EntriesState, dispatch: (EntriesEvent) -> Unit) {
    Scaffold { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .padding(Dimens.screenPadding)
                .fillMaxSize()
        ) {
            Filters(dispatch)
            LazyColumn(
                verticalArrangement = Arrangement.spacedBy(Dimens.standard)
            ) {
                items(state.entries.size, { state.entries[it].id }) {
                    val item = state.entries[it]
                    EntryCompact(item)
                }
            }
        }
    }
}

@Composable
private fun Filters(
    dispatch: (EntriesEvent) -> Unit
) {

}