package io.github.staakk.nptracker.domain

class GetAvailableExercises(
    private val workoutsDataSource: WorkoutsDataSource
) {

    suspend operator fun invoke(): Result<List<String>> =
        workoutsDataSource.getAvailableExercises()
}