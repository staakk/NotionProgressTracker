package io.github.staakk.nptracker.entries

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.staggeredgrid.LazyHorizontalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.outlined.Add
import androidx.compose.material.icons.outlined.Clear
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
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
    Scaffold(
        floatingActionButton = {
            FloatingActionButton(
                onClick = {},
                shape = RoundedCornerShape(100),
            ) {
                Row(
                    modifier = Modifier
                        .padding(Dimens.standard),
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    Icon(Icons.Filled.Add, contentDescription = null)
                    Spacer(Modifier.width(Dimens.half))
                    Text("Add new")
                }

            }
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .padding(Dimens.screenPadding)
                .fillMaxSize()
        ) {
            Filters(dispatch)
            LazyColumn(
                modifier = Modifier.weight(1f),
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
private fun ColumnScope.Filters(
    dispatch: (EntriesEvent) -> Unit
) {
    LazyHorizontalStaggeredGrid(
        modifier = Modifier.weight(0.16f),
        rows = StaggeredGridCells.Adaptive(30.dp),
        horizontalItemSpacing = 8.dp,
        verticalArrangement = Arrangement.spacedBy(8.dp),
        contentPadding = PaddingValues(bottom = 16.dp)
    ) {
        item {
            FilterChip(
                selected = false,
                onClick = {},
                leadingIcon = { Icon(Icons.Outlined.Add, contentDescription = null) },
                label = { Text("Add filter") }
            )
        }
        item {
            FilterChip(
                selected = true,
                onClick = {},
                trailingIcon = {
                    Icon(
                        Icons.Outlined.Clear,
                        modifier = Modifier.padding(4.dp),
                        contentDescription = null
                    )
                },
                label = { Text("20 items") }
            )
        }
        item {
            FilterChip(
                selected = true,
                onClick = {},
                trailingIcon = {
                    Icon(
                        Icons.Outlined.Clear,
                        modifier = Modifier.padding(4.dp),
                        contentDescription = null
                    )
                },
                label = { Text("Dead lift") }
            )
        }
    }
}