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
import io.github.staakk.nptracker.Dimens
import io.github.staakk.nptracker.Entry
import io.github.staakk.nptracker.EntryCompact
import io.github.staakk.nptracker.framework.DateInput
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

@Composable
fun EntryScreen() {
    Scaffold {
        Column(
            modifier = Modifier
                .padding(it)
                .padding(Dimens.screenPadding)
                .fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(Dimens.standard)
        ) {
            Exercise()
            Repetitions()
            Weight()
            Date(
                date = Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault()).date,
                onDateChanged = {}
            )
            LastEntries("Dead lift")
            Spacer(Modifier.weight(1f))
            ConfirmButton { }
        }
    }
}

@Composable
private fun ColumnScope.LastEntries(
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
                    Entry(
                        exercise = "Dead lift",
                        repetitions = 10,
                        weight = 120f,
                        date = Clock.System.now()
                            .toLocalDateTime(TimeZone.currentSystemDefault()),
                    )
                )
            }
        }
    }
}

@Composable
private fun Exercise() {
    Box {
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
            value = "Dead lift",
            onValueChange = {},
            label = { Text(stringResource(Res.string.screen_entry_label_exercise)) },
            keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Text),
        )
        DropdownMenu(
            modifier = Modifier.width(width),
            expanded = isFocused,
            onDismissRequest = { isFocused = false },
            properties = PopupProperties()
        ) {
            DropdownMenuItem(
                text = { Text("test 1") },
                onClick = {}
            )
            DropdownMenuItem(
                text = { Text("test 2") },
                onClick = {}
            )
            DropdownMenuItem(
                text = { Text("test 3") },
                onClick = {}
            )
        }
    }
}

@Composable
private fun Repetitions() {
    OutlinedTextField(
        modifier = Modifier.fillMaxWidth(),
        value = "12",
        onValueChange = {},
        label = { Text(stringResource(Res.string.screen_entry_label_repetitions)) },
        keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number)
    )
}

@Composable
private fun Weight() {
    OutlinedTextField(
        modifier = Modifier.fillMaxWidth(),
        value = "120",
        onValueChange = {},
        label = { Text(stringResource(Res.string.screen_entry_label_weight)) },
        keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Decimal)
    )
}

@Composable
private fun Date(
    date: LocalDate,
    onDateChanged: (LocalDate) -> Unit
) {
    DateInput(
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
