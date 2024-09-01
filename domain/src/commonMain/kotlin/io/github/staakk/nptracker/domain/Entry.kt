package io.github.staakk.nptracker.domain

import kotlinx.datetime.Clock
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime

data class Entry(
    val id: EntryId,
    val exercise: String = "",
    val repetitions: Repetitions = Repetitions(0),
    val weight: Weight = 0.kg,
    val date: LocalDateTime = Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault()),
)