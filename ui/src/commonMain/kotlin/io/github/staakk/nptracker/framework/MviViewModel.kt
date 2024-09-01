package io.github.staakk.nptracker.framework

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch

class ActionResult<State, Event>(
    val state: State?,
    val event: Event?,
)

fun interface Action<State, Event> {
    suspend fun execute(state: State): ActionResult<State, Event>
}

abstract class MviViewModel<State, Event>(initial: State) : ViewModel() {
    private val mutableEvents = MutableSharedFlow<Event>()
    private val mutableState: MutableStateFlow<State> =
        MutableStateFlow(initial)
    val state: StateFlow<State> = mutableState.asStateFlow()

    protected abstract suspend fun handleEvent(event: Event): Action<State, Event>

    init {
        mutableEvents
            .onEach { event ->
                handleEvent(event)
                    .execute(state.value)
                    .let { result ->
                        result.state?.let { mutableState.emit(it) }
                        result.event?.let { dispatch(it) }
                    }
            }
            .launchIn(viewModelScope)
    }

    fun dispatch(event: Event) {
        viewModelScope.launch { mutableEvents.emit(event) }
    }

    protected suspend inline fun transform(
        crossinline f: suspend (State) -> State,
    ) = Action { state: State ->
        ActionResult(f(state), null as Event?)
    }

    protected suspend inline fun consume(
        crossinline f: suspend (State) -> Unit,
    ) = Action { state: State ->
        f(state)
        ActionResult(state, null as Event?)
    }
}