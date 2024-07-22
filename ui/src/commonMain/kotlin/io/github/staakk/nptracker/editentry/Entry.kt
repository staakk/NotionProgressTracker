package io.github.staakk.nptracker.editentry

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
import io.github.staakk.nptracker.Dimens
import io.github.staakk.nptracker.domain.Repetitions
import io.github.staakk.nptracker.domain.Weight
import io.github.staakk.nptracker.domain.WeightFormatter
import io.github.staakk.nptracker.domain.kg
import io.github.staakk.nptracker.framework.Format
import kotlinx.datetime.Clock
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime
import notionprogresstracker.ui.generated.resources.Res
import notionprogresstracker.ui.generated.resources.reps_times_weight_kg
import org.jetbrains.compose.resources.stringResource
import kotlin.math.pow
import kotlin.math.roundToInt

@Composable
fun EntryCompact(
    item: Entry,
    exercise: @Composable (String) -> Unit = { Text(it) }
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
        exercise(item.exercise)
        Text(
            color = MaterialTheme.colorScheme.onSecondaryContainer,
            fontWeight = FontWeight.Bold,
            text = stringResource(
                Res.string.reps_times_weight_kg,
                item.repetitions.value,
                WeightFormatter.format(item.weight)
            )
        )
        Text(Format.dateDefault.format(item.date.date))
    }
}


data class Entry(
    val exercise: String = "",
    val repetitions: Repetitions = Repetitions(0),
    val weight: Weight = 0.kg,
    val date: LocalDateTime = Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault()),
)