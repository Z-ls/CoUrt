package it.polito.mad.court.composable

import android.app.TimePickerDialog
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import java.time.LocalTime

@Composable
fun ButtonTimePicker(selectedTime: LocalTime, onTimeChange: (LocalTime) -> Unit) {

    val context = LocalContext.current

    val timePickerDialog = TimePickerDialog(
        context,
        { _, hourSel, minuteSel ->
            onTimeChange(LocalTime.of(hourSel, minuteSel))
        },
        selectedTime.hour,
        selectedTime.minute,
        true
    )

    OutlinedButton(
        content = {
            Text(
                color = Color.Black,
                text = "${selectedTime.hour} : ${selectedTime.minute}"
            )
        },
        onClick = { timePickerDialog.show() }
    )
}

@Preview
@Composable
fun TimePickerPreview() {
    val time = remember { mutableStateOf(LocalTime.now()) }
    ButtonTimePicker(time.value) {
        time.value = it
    }
}