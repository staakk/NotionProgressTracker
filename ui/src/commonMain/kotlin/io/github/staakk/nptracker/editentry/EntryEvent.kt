package io.github.staakk.nptracker.editentry

import io.github.staakk.nptracker.domain.Repetitions
import io.github.staakk.nptracker.domain.Weight
import kotlinx.datetime.LocalDate

sealed class EntryEvent {
    data object ScreenLaunched : EntryEvent()
    data class ExerciseChanged(val exercise: String) : EntryEvent()
    data class RepetitionsChanged(val repetitions: Repetitions) : EntryEvent()
    data class WeightChanged(val weight: Weight) : EntryEvent()
    data class DateChanged(val date: LocalDate) : EntryEvent()
    data object ConfirmClicked: EntryEvent()
}