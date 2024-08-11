package io.github.staakk.nptracker.domain.usecase

import io.github.staakk.nptracker.domain.WorkoutsDataSource

class GetAvailableExercises(
    private val workoutsDataSource: WorkoutsDataSource
) {

    suspend operator fun invoke(): Result<List<String>> =
        workoutsDataSource.getAvailableExercises()
}