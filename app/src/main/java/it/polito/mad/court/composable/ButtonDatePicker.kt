package it.polito.mad.court.composable

import android.app.DatePickerDialog
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import java.time.LocalDate

@Composable
fun ButtonDatePicker(selectedDate: LocalDate, onTimeChange: (LocalDate) -> Unit) {

    val context = LocalContext.current

    val datePickerDialog = DatePickerDialog(
        context,
        { _, yearSel, monthSel, daySel ->
            onTimeChange(LocalDate.of(yearSel, monthSel + 1, daySel))
        },
        selectedDate.year, selectedDate.monthValue - 1, selectedDate.dayOfMonth
    )

    OutlinedButton(
        content = {
            Text(
                color = Color.Black,
                text = "${selectedDate.dayOfMonth} / ${selectedDate.monthValue} / ${selectedDate.year}"
            )
        },
        onClick = { datePickerDialog.show() }
    )
}

@Preview
@Composable
fun ButtonDatePickerPreview() {
    val date = remember { mutableStateOf(LocalDate.now()) }
    ButtonDatePicker(date.value) {
        date.value = it
    }
}