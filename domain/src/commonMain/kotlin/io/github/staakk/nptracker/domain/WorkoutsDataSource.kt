package io.github.staakk.nptracker.domain

interface WorkoutsDataSource {

    suspend fun getAvailableExercises(): Result<List<String>>
}