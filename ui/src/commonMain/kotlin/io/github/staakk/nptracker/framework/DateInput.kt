package io.github.staakk.nptracker.framework

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.OutlinedTextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.TextRange
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.OffsetMapping
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.input.TransformedText
import androidx.compose.ui.text.input.VisualTransformation
import kotlinx.datetime.DatePeriod
import kotlinx.datetime.LocalDate
import kotlinx.datetime.minus
import kotlinx.datetime.plus

private val dateFormat = LocalDate.Format {
    dayOfMonth()
    monthNumber()
    yearTwoDigits(2000)
}

@Composable
fun DateInput(
    modifier: Modifier = Modifier,
    date: LocalDate,
    onDateChanged: (LocalDate) -> Unit,
    label: @Composable () -> Unit = {},
) {
    var input by remember { mutableStateOf(TextFieldValue(dateFormat.format(date))) }
    OutlinedTextField(
        modifier = Modifier.fillMaxWidth().then(modifier),
        value = input,
        visualTransformation = visualTransformation,
        onValueChange = { newInput ->
            onValueChanged(input, newInput)?.let { result ->
                if (input.text != result.text) onDateChanged(dateFormat.parse(result.text))
                input = result
            }
        },
        label = label,
        keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number)
    )
}

private val visualTransformation = VisualTransformation { text ->
    TransformedText(
        offsetMapping = offsetMapping,
        text = AnnotatedString(
            buildString {
                append(text.take(2))
                append('/')
                append(text.subSequence(2..3))
                append('/')
                append(text.subSequence(4..5))
            }
        ),
    )
}
private val offsetMapping = object : OffsetMapping {
    override fun originalToTransformed(offset: Int): Int = when {
        offset >= 4 -> offset + 2
        offset >= 2 -> offset + 1
        else -> offset
    }.coerceAtMost(7)

    override fun transformedToOriginal(offset: Int): Int = when {
        offset >= 6 -> offset - 3
        offset >= 3 -> offset - 1
        else -> offset
    }.coerceAtMost(9)
}

private fun onValueChanged(input: TextFieldValue, newInput: TextFieldValue): TextFieldValue? {
    val newText = newInput.text
    val text = input.text
    if (newText.length == text.length) return newInput
    if (newText.any { !it.isDigit() }) return null
    if (newText.length < text.length) return newInput.copy(text = text)

    val result = newText
        .let {
            CharArray(6) { index -> text[index] }.apply {
                val enteredCharIndex = (newInput.selection.min - 1)
                set(enteredCharIndex, it[enteredCharIndex])
            }
        }
        .correctDate()

    return newInput.copy(
        text = result,
        selection = TextRange(newInput.selection.min.coerceAtMost(5)),
    )
}

private fun CharArray.correctDate(): String = let { output ->
    // First digit of a month cannot be larger than 1
    if (output[2] > '1') output[2] = '1'
    // Month cannot be larger than 12
    if (output[2] == '1' && output[3] > '2') output[3] = '2'
    // Month cannot be 00
    if (output[2] == '0' && output[3] == '0') output[3] = '1'

    // Now date is correct to the month, and the day can be corrected
    val firstOfMonth = output.dateAtFirstOfMonth()
    val maxDay = dateFormat.parse(firstOfMonth)
        .maxDayOfMonth()
        .toString()
    // First digit of day cannot be larger than max day first digit
    if (output[0] > maxDay[0]) {
        output[0] = maxDay[0]
        output[1] = maxDay[1]
    }
    // Second digit of day cannot be larger than max day second digit
    if (output[0] == maxDay[0] && output[1] > maxDay[1]) output[1] = maxDay[1]
    // Day cannot be 00
    if (output[0] == '0' && output[1] == '0') output[1] = '1'

    output.concatToString()
}

private fun CharArray.dateAtFirstOfMonth() =
    copyOf()
        .also { it[0] = '0'; it[1] = '1' }
        .concatToString()

private fun LocalDate.maxDayOfMonth() =
    plus(DatePeriod(months = 1))
        .minus(DatePeriod(days = 1))
        .dayOfMonth
