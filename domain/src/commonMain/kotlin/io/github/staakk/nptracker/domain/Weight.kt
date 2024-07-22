package io.github.staakk.nptracker.domain

import kotlin.jvm.JvmInline
import kotlin.math.pow
import kotlin.math.roundToInt

@JvmInline
value class Weight(val grams: Int) {
    init {
        require(grams >= 0) { "Weight must be positive. Got `$this`." }
    }
    val kg get() = grams / 1000f
}

val Number.kg: Weight
    get() = Weight((this.toFloat() * 1000).toInt())

object WeightFormatter {
    enum class WeightUnit {
        Kg
    }
    fun format(weight: Weight, unit: WeightUnit = WeightUnit.Kg, decimalPlaces: Int = 2): String {
        return when (unit) {
            WeightUnit.Kg -> weight.kg
        }
            .roundToDecimals(decimalPlaces)
            .toString()
    }

    private fun Float.roundToDecimals(decimals: Int): Float {
        val dotAt = 10.0f.pow(decimals)
        val roundedValue = (this * dotAt).roundToInt()
        return (roundedValue / dotAt) + (roundedValue % dotAt) / dotAt
    }
}
