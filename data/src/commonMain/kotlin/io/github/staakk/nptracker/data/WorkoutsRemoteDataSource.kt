package io.github.staakk.nptracker.data

import io.github.staakk.nptracker.domain.WorkoutsDataSource

private const val DatabaseIdWorkouts = "b93c531c-7cd4-47c4-8264-1aad5e7468cd"
private const val PropertyExercise = "Exercise"

class WorkoutsRemoteDataSource(
    private val notionService: NotionService
) : WorkoutsDataSource {

    override suspend fun getAvailableExercises(): Result<List<String>> = notionService
        .getDatabase(DatabaseIdWorkouts)
        .mapCatching { it.properties[PropertyExercise] as Property.Select }
        .map { prop -> prop.select.options.map { it.name } }
}