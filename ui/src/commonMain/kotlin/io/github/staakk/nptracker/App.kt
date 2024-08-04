package io.github.staakk.nptracker

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import io.github.staakk.nptracker.data.dataModule
import io.github.staakk.nptracker.domain.domainModule
import io.github.staakk.nptracker.editentry.EntryScreen
import org.koin.compose.KoinApplication

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
                EntryScreen()
            }
        }
    }
}