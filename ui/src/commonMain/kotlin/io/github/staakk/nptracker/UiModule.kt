package io.github.staakk.nptracker

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberUpdatedState
import io.github.staakk.nptracker.data.dataModule
import io.github.staakk.nptracker.domain.domainModule
import io.github.staakk.nptracker.editentry.EntryViewModel
import io.github.staakk.nptracker.entries.EntriesViewModel
import org.koin.compose.currentKoinScope
import org.koin.core.module.dsl.factoryOf
import org.koin.core.parameter.ParametersHolder
import org.koin.core.parameter.emptyParametersHolder
import org.koin.core.qualifier.Qualifier
import org.koin.core.scope.Scope
import org.koin.dsl.module

typealias ParametersDefinition = () -> ParametersHolder

val uiModule = module {
    includes(
        dataModule,
        domainModule
    )

    factoryOf(::EntryViewModel)
    factoryOf(::EntriesViewModel)
}

@Composable
inline fun <reified T> koinViewModelFactory(
    qualifier: Qualifier? = null,
    scope: Scope = currentKoinScope(),
    noinline parameters: ParametersDefinition? = null,
): T {
    val currentParameters by rememberUpdatedState(parameters)

    return remember(qualifier, scope) {
        scope.get(qualifier) {
            currentParameters?.invoke() ?: emptyParametersHolder()
        }
    }
}