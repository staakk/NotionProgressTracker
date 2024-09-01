package io.github.staakk.nptracker.entries

import androidx.navigation.NavController
import io.github.staakk.nptracker.Screen
import io.github.staakk.nptracker.domain.EntryId

class EntriesScreenNavigator(
    val onAddEntry: () -> Unit,
    val onEditEntry: (EntryId) -> Unit
) {
    constructor(navController: NavController): this(
        onAddEntry = { navController.navigate(Screen.Entry(null)) },
        onEditEntry = { navController.navigate(Screen.Entry(it)) }
    )
}