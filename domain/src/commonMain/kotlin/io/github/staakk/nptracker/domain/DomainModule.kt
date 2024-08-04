package io.github.staakk.nptracker.domain

import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

val domainModule = module {
    singleOf(::GetAvailableExercises)
}