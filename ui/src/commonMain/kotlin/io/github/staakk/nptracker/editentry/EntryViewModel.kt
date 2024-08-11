package io.github.staakk.nptracker.editentry

import io.github.staakk.nptracker.domain.Entry
import io.github.staakk.nptracker.domain.usecase.GetAvailableExercises
import io.github.staakk.nptracker.editentry.EntryEvent.ConfirmClicked
import io.github.staakk.nptracker.editentry.EntryEvent.DateChanged
import io.github.staakk.nptracker.editentry.EntryEvent.ExerciseChanged
import io.github.staakk.nptracker.editentry.EntryEvent.RepetitionsChanged
import io.github.staakk.nptracker.editentry.EntryEvent.WeightChanged
import io.github.staakk.nptracker.framework.MviViewModel
import kotlinx.datetime.LocalTime
import kotlinx.datetime.atTime

internal class EntryViewModel(
    private val getAvailableExercises: GetAvailableExercises,
) : MviViewModel<EntryScreenState, EntryEvent>(
    EntryScreenState(
        entry = Entry(
            exercise = "Dead lift",
        ),
        availableExercises = listOf(
            "Dead lift",
            "Bench press",
            "Squat",
        )
    )
) {

    init {
        dispatch(EntryEvent.ScreenLaunched)
    }

    override suspend fun handleEvent(event: EntryEvent) = when (event) {
        is EntryEvent.ScreenLaunched -> action {
                it.copy(availableExercises = getAvailableExercises().getOrDefault(emptyList()))
        }

        is RepetitionsChanged ->
            action { it.copy(entry = it.entry.copy(repetitions = event.repetitions)) }

        is ExerciseChanged -> action { it.copy(entry = it.entry.copy(exercise = event.exercise)) }
        is WeightChanged -> action { it.copy(entry = it.entry.copy(weight = event.weight)) }
        is ConfirmClicked -> action { it.copy(isLoading = true) }

        is DateChanged -> action {
            it.copy(
                entry = it.entry.copy(
                    date = event.date.atTime(LocalTime(0, 0, 0))
                )
            )
        }
    }
}