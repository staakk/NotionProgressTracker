package io.github.staakk.nptracker

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import io.github.staakk.nptracker.data.dataModule
import io.github.staakk.nptracker.domain.EntryId
import io.github.staakk.nptracker.domain.domainModule
import io.github.staakk.nptracker.editentry.EntryScreen
import io.github.staakk.nptracker.entries.EntriesScreen
import io.github.staakk.nptracker.entries.EntriesScreenNavigator
import io.github.staakk.nptracker.framework.navigation.EntryIdNavType
import kotlinx.serialization.Serializable
import org.koin.compose.KoinApplication
import kotlin.reflect.typeOf

@Composable
fun App() {
    KoinApplication(application = {
        modules(dataModule, domainModule, uiModule)
    }) {
        AppTheme {
            Surface(
                modifier = Modifier.fillMaxSize(),
                color = MaterialTheme.colorScheme.background
            ) {
                Navigation()
            }
        }
    }
}

@Composable
private fun Navigation() {
    val navController = rememberNavController()
    NavHost(navController, startDestination = Screen.Entries) {
        composable<Screen.Entries> {
            EntriesScreen(EntriesScreenNavigator(navController))
        }
        composable<Screen.Entry>(typeMap = mapOf(EntryIdNavType.typeMap)) {
            EntryScreen()
        }
    }
}

sealed interface Screen {
    @Serializable
    data object Entries : Screen
    @Serializable
    data class Entry(val entryId: EntryId?) : Screen
}