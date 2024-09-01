package io.github.staakk.nptracker.editentry

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.intl.Locale
import androidx.compose.ui.text.toLowerCase
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.PopupProperties
import androidx.lifecycle.viewmodel.compose.viewModel
import io.github.staakk.nptracker.Dimens
import io.github.staakk.nptracker.domain.Entry
import io.github.staakk.nptracker.domain.EntryId
import io.github.staakk.nptracker.domain.Repetitions
import io.github.staakk.nptracker.domain.Weight
import io.github.staakk.nptracker.domain.WeightFormatter
import io.github.staakk.nptracker.domain.kg
import io.github.staakk.nptracker.framework.DateField
import io.github.staakk.nptracker.shared.EntryCompact
import kotlinx.datetime.Clock
import kotlinx.datetime.LocalDate
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime
import notionprogresstracker.ui.generated.resources.Res
import notionprogresstracker.ui.generated.resources.screen_entry_button_confirm
import notionprogresstracker.ui.generated.resources.screen_entry_label_date
import notionprogresstracker.ui.generated.resources.screen_entry_label_exercise
import notionprogresstracker.ui.generated.resources.screen_entry_label_repetitions
import notionprogresstracker.ui.generated.resources.screen_entry_label_weight
import org.jetbrains.compose.resources.stringResource
import org.koin.compose.currentKoinScope

@Composable
fun EntryScreen() {
    val koinScope = currentKoinScope()
    val viewModel = viewModel { koinScope.get<EntryViewModel>() }
    val state by viewModel.state.collectAsState()
    ScreenContent(state, viewModel::dispatch)
}

@Composable
private fun ScreenContent(state: EntryScreenState, dispatch: (EntryEvent) -> Unit) {
    Scaffold { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .padding(Dimens.screenPadding)
                .fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(Dimens.standard)
        ) {
            with(state.entry) {
                ExerciseSelection(
                    exercise,
                    state.availableExercises,
                ) { dispatch(EntryEvent.ExerciseChanged(it)) }
                Repetitions(repetitions) { dispatch(EntryEvent.RepetitionsChanged(it)) }
                WeightField(
                    weight,
                    onValueChanged = { dispatch(EntryEvent.WeightChanged(it)) }
                )
                Date(
                    date = date.date,
                    onDateChanged = { dispatch(EntryEvent.DateChanged(it)) }
                )
            }
            LastEntries(state.entry.exercise)
            Spacer(Modifier.weight(1f))
            ConfirmButton { dispatch(EntryEvent.ConfirmClicked) }
        }
    }
}

@Composable
private fun LastEntries(
    exerciseName: String,
) {
    Text(
        text = "Last entries for ${exerciseName.toLowerCase(Locale.current)}",
        style = MaterialTheme.typography.titleSmall,
    )
    LazyColumn(
        verticalArrangement = Arrangement.spacedBy(Dimens.standard)
    ) {
        repeat(3) {
            item {
                EntryCompact(
                    item = Entry(
                        id = EntryId(it.toString()),
                        exercise = "Dead lift",
                        repetitions = Repetitions(10),
                        weight = 120f.kg,
                        date = Clock.System.now()
                            .toLocalDateTime(TimeZone.currentSystemDefault()),
                    ),
                    exercise = {},
                )
            }
        }
    }
}

@Composable
private fun ExerciseSelection(
    exercise: String,
    availableExercises: List<String>,
    onValueChanged: (String) -> Unit,
) {
    Box {
        var enteredValue: String by remember { mutableStateOf(exercise) }
        var isFocused: Boolean by remember { mutableStateOf(false) }
        var width by remember { mutableStateOf(0.dp) }
        val density = LocalDensity.current
        OutlinedTextField(
            modifier = Modifier
                .fillMaxWidth()
                .onFocusChanged { isFocused = it.hasFocus }
                .onGloballyPositioned {
                    width = with(density) { it.size.width.toDp() }
                },
            value = exercise,
            onValueChange = { newValue ->
                enteredValue = availableExercises
                    .firstOrNull { it.startsWith(newValue) }
                    ?: enteredValue
                if (enteredValue in availableExercises) onValueChanged(newValue)
            },
            label = { Text(stringResource(Res.string.screen_entry_label_exercise)) },
            keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Text),
        )
        DropdownMenu(
            modifier = Modifier.width(width),
            expanded = isFocused,
            onDismissRequest = { isFocused = false },
            properties = PopupProperties()
        ) {
            availableExercises.forEach {
                DropdownMenuItem(
                    text = { Text(it) },
                    onClick = {
                        enteredValue = it
                        onValueChanged(it)
                    }
                )
            }
        }
    }
}

@Composable
private fun Repetitions(
    repetitions: Repetitions,
    onValueChanged: (Repetitions) -> Unit
) {
    OutlinedTextField(
        modifier = Modifier.fillMaxWidth(),
        value = repetitions.value.toString(),
        onValueChange = { text ->
            text
                .toIntOrNull()
                ?.let { onValueChanged(Repetitions(it)) }
        },
        label = { Text(stringResource(Res.string.screen_entry_label_repetitions)) },
        keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number)
    )
}

@Composable
private fun WeightField(
    weight: Weight,
    onValueChanged: (Weight) -> Unit
) {
    var text by remember { mutableStateOf(WeightFormatter.format(weight)) }
    OutlinedTextField(
        modifier = Modifier.fillMaxWidth(),
        value = weight.kg.toString(),
        onValueChange = {
            text = it
            onValueChanged(it.toFloatOrNull()?.kg ?: Weight(0))
        },
        label = { Text(stringResource(Res.string.screen_entry_label_weight)) },
        keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Decimal)
    )
}

@Composable
private fun Date(
    date: LocalDate,
    onDateChanged: (LocalDate) -> Unit
) {
    DateField(
        date = date,
        onDateChanged = onDateChanged,
        label = { Text(stringResource(Res.string.screen_entry_label_date)) }
    )
}

@Composable
private fun ColumnScope.ConfirmButton(onClick: () -> Unit) {
    Button(
        modifier = Modifier.align(Alignment.End),
        onClick = onClick,
        content = { Text(stringResource(Res.string.screen_entry_button_confirm)) }
    )
}
