package io.github.staakk.nptracker.domain.usecase

import io.github.staakk.nptracker.domain.Entry
import io.github.staakk.nptracker.domain.WorkoutsDataSource

class GetLatestEntries(
    private val workoutsDataSource: WorkoutsDataSource,
) {
    suspend operator fun invoke(): Result<List<Entry>> =
        workoutsDataSource.queryEntries()
}