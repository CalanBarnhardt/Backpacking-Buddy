package pangolin.backpackingbuddy.ui.createTrip

import android.app.DatePickerDialog
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.clickable
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.platform.LocalContext
import pangolin.backpackingbuddy.data.Trip
import java.util.*

@Composable
fun CreateNewTripDate(onGetStarted: (Trip) -> Unit) {
    val startDate = remember { mutableStateOf(TextFieldValue()) }
    val endDate = remember { mutableStateOf(TextFieldValue()) }
    val context = LocalContext.current
    var selectedDate by remember { mutableStateOf("") }

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(16.dp),
        modifier = Modifier.fillMaxSize().padding(16.dp)
    ) {
        Text(text = "When will this trip be?", style = androidx.compose.material3.MaterialTheme.typography.titleLarge)

        DateInputField(
            label = "Start: ",
            value = startDate.value,
            onValueChange = { startDate.value = it },
            onClick = {
                showDatePicker(context, { selectedDate = it; startDate.value = TextFieldValue(it) })
            }
        )

        DateInputField(
            label = "End: ",
            value = endDate.value,
            onValueChange = { endDate.value = it },
            onClick = {
                showDatePicker(context, { selectedDate = it; endDate.value = TextFieldValue(it) })
            }
        )

        Button(
            onClick = {
                Toast.makeText(context, "Your trip has been created", Toast.LENGTH_SHORT).show()
                      },
            modifier = Modifier.padding(top = 16.dp)
        ) {
            Text(text = "Get Started")
        }
    }
}

@Composable
fun DateInputField(
    label: String,
    value: TextFieldValue,
    onValueChange: (TextFieldValue) -> Unit,
    onClick: () -> Unit
) {
    BasicTextField(
        value = value,
        onValueChange = onValueChange,
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
            .clickable { onClick() },
        decorationBox = { innerTextField ->
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(text = label)
                Spacer(modifier = Modifier.width(8.dp))
                innerTextField()
            }
        }
    )
}

fun showDatePicker(context: android.content.Context, onDateSelected: (String) -> Unit) {
    val calendar = Calendar.getInstance()
    val year = calendar.get(Calendar.YEAR)
    val month = calendar.get(Calendar.MONTH)
    val dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH)

    val datePickerDialog = DatePickerDialog(
        context,
        { _, selectedYear, selectedMonth, selectedDay ->
            val formattedDate = "${selectedMonth + 1}/${selectedDay}/${selectedYear}"
            onDateSelected(formattedDate)
        },
        year,
        month,
        dayOfMonth
    )

    datePickerDialog.show()
}

@Preview
@Composable
fun PreviewNameThisTripScreen() {
    CreateNewTripDate({})
}
