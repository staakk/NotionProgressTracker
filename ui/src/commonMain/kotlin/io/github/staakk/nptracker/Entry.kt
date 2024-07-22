package io.github.staakk.nptracker

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import io.github.staakk.nptracker.framework.Format
import kotlinx.datetime.LocalDateTime
import notionprogresstracker.ui.generated.resources.Res
import notionprogresstracker.ui.generated.resources.reps_times_weight_kg
import org.jetbrains.compose.resources.stringResource
import kotlin.math.roundToInt

@Composable
fun EntryCompact(
    item: Entry,
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(
                MaterialTheme.colorScheme.secondaryContainer,
                RoundedCornerShape(Dimens.standard),
            )
            .padding(horizontal = Dimens.standard, vertical = Dimens.half),
        horizontalArrangement = Arrangement.SpaceBetween,
    ) {
        Text(item.exercise)
        Text(
            color = MaterialTheme.colorScheme.onSecondaryContainer,
            fontWeight = FontWeight.Bold,
            text = stringResource(
                Res.string.reps_times_weight_kg,
                item.repetitions,
                item.weight.roundToDecimals(2)
            )
        )
        Text(Format.dateDefault.format(item.date.date))
    }
}

private fun Float.roundToDecimals(decimals: Int): Float {
    var dotAt = 1
    repeat(decimals) { dotAt *= 10 }
    val roundedValue = (this * dotAt).roundToInt()
    return (roundedValue / dotAt) + (roundedValue % dotAt).toFloat() / dotAt
}

data class Entry(
    val exercise: String,
    val repetitions: Int,
    val weight: Float,
    val date: LocalDateTime,
)