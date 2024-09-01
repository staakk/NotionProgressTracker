package io.github.staakk.nptracker.editentry

import io.github.staakk.nptracker.domain.Entry
import io.github.staakk.nptracker.domain.EntryId
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
            id = EntryId("fake_id"),
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
        is EntryEvent.ScreenLaunched -> transform {
                it.copy(availableExercises = getAvailableExercises().getOrDefault(emptyList()))
        }

        is RepetitionsChanged ->
            transform { it.copy(entry = it.entry.copy(repetitions = event.repetitions)) }

        is ExerciseChanged -> transform { it.copy(entry = it.entry.copy(exercise = event.exercise)) }
        is WeightChanged -> transform { it.copy(entry = it.entry.copy(weight = event.weight)) }
        is ConfirmClicked -> transform { it.copy(isLoading = true) }

        is DateChanged -> transform {
            it.copy(
                entry = it.entry.copy(
                    date = event.date.atTime(LocalTime(0, 0, 0))
                )
            )
        }
    }
}