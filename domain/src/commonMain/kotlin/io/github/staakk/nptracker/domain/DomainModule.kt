package io.github.staakk.nptracker.domain

import io.github.staakk.nptracker.domain.usecase.GetAvailableExercises
import io.github.staakk.nptracker.domain.usecase.GetLatestEntries
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

val domainModule = module {
    singleOf(::GetAvailableExercises)
    singleOf(::GetLatestEntries)
}