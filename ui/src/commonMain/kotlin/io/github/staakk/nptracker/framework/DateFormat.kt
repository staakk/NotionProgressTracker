package io.github.staakk.nptracker.framework

import kotlinx.datetime.LocalDate
import kotlinx.datetime.format.MonthNames
import kotlinx.datetime.format.Padding
import kotlinx.datetime.format.char

object Format {
    val dateDefault = LocalDate.Format {
        dayOfMonth(Padding.NONE)
        char(' ')
        monthName(MonthNames.ENGLISH_ABBREVIATED)
        char(' ')
        year(Padding.NONE)
    }
    val dateInputInternal = LocalDate.Format {
        dayOfMonth()
        monthNumber()
        yearTwoDigits(2000)
    }
}