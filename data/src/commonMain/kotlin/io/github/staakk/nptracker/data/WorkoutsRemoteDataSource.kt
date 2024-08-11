package io.github.staakk.nptracker.data

import io.github.staakk.nptracker.data.model.JsonColumnProperty
import io.github.staakk.nptracker.data.model.JsonQueryRequest
import io.github.staakk.nptracker.data.model.JsonResultProperty
import io.github.staakk.nptracker.domain.Entry
import io.github.staakk.nptracker.domain.Repetitions
import io.github.staakk.nptracker.domain.Weight
import io.github.staakk.nptracker.domain.WorkoutsDataSource
import kotlinx.datetime.LocalDate
import kotlinx.datetime.TimeZone
import kotlinx.datetime.atStartOfDayIn
import kotlinx.datetime.format.char
import kotlinx.datetime.toLocalDateTime

private const val DatabaseIdWorkouts = "b93c531c-7cd4-47c4-8264-1aad5e7468cd"
private const val PropertyExercise = "Exercise"
private const val PropertyRepetitions = "Repetitions"
private const val PropertyWeight = "Weight [kg]"
private const val PropertyDate = "Date"

private val PropertyDateFormatter = LocalDate.Format {
    year()
    char('-')
    monthNumber()
    char('-')
    dayOfMonth()
}

class WorkoutsRemoteDataSource(
    private val notionService: NotionService
) : WorkoutsDataSource {

    override suspend fun getAvailableExercises(): Result<List<String>> = notionService
        .getDatabase(DatabaseIdWorkouts)
        .mapCatching { it.properties[PropertyExercise] as JsonColumnProperty.Select }
        .map { prop -> prop.select.options.map { it.name } }

    override suspend fun queryEntries(): Result<List<Entry>> = notionService
        .queryEntries(
            request = JsonQueryRequest(pageSize = 5),
            databaseId = DatabaseIdWorkouts,
        )
        .mapCatching { response ->
            response.results.map {
                Entry(
                    id = it.id,
                    exercise = (it.properties[PropertyExercise] as JsonResultProperty.Select).select.name,
                    repetitions = (it.properties[PropertyRepetitions] as JsonResultProperty.Number).number.toInt()
                        .let(::Repetitions),
                    weight = (it.properties[PropertyWeight] as JsonResultProperty.Number)
                        .number
                        .let { weightKg -> weightKg * 1000 }
                        .toInt()
                        .let(::Weight),
                    date = (it.properties[PropertyDate] as JsonResultProperty.Date).date.start
                        .let { date ->
                            PropertyDateFormatter.parse(date)
                                .atStartOfDayIn(TimeZone.currentSystemDefault())
                                .toLocalDateTime(TimeZone.currentSystemDefault())
                        }
                )
            }
        }
        .onFailure { it.printStackTrace() }
}