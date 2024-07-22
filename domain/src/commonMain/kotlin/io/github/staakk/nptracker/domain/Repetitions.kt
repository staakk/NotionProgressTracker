package io.github.staakk.nptracker.domain

import kotlin.jvm.JvmInline

@JvmInline
value class Repetitions(val value: Int) {
    init {
        require(value >= 0) { "Repetitions must be positive. Got `$value`."}
    }
}