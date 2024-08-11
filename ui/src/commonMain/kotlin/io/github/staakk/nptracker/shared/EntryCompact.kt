package io.github.staakk.nptracker.shared


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
import io.github.staakk.nptracker.domain.Entry
import io.github.staakk.nptracker.domain.WeightFormatter
import io.github.staakk.nptracker.framework.Format
import notionprogresstracker.ui.generated.resources.Res
import notionprogresstracker.ui.generated.resources.reps_times_weight_kg
import org.jetbrains.compose.resources.stringResource

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