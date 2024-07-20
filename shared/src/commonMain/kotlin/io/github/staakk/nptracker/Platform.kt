package io.github.staakk.nptracker

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform