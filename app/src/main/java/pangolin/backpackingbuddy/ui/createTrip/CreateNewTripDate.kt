package pangolin.backpackingbuddy.ui.createTrip

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.ui.unit.dp
import pangolin.backpackingbuddy.ui.sharedComponents.NavButton
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.DateRangePicker
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.TextButton
import androidx.compose.material3.rememberDateRangePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import pangolin.backpackingbuddy.viewmodel.BackpackingBuddyViewModel
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import java.time.LocalDate
import java.time.format.DateTimeFormatter

@Composable
fun CreateNewTripDate(viewModel: BackpackingBuddyViewModel,
                      tripName: String,
                      onGetStarted: () -> Unit) {
    var showRangePicker by remember { mutableStateOf(false) }

    var startDate by remember { mutableStateOf(
        LocalDate.now().format(DateTimeFormatter.ofPattern("MM/dd/yyyy"))
    ) }
    var endDate by remember { mutableStateOf(
        LocalDate.now().plusDays(7).format(DateTimeFormatter.ofPattern("MM/dd/yyyy"))
    ) }

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(16.dp),
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .background(MaterialTheme.colorScheme.background)
    ) {

        Text(
            text = "When will this trip be?",
            style = MaterialTheme.typography.titleLarge
        )

        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(
                text = "Select Dates:",
                style = MaterialTheme.typography.bodyLarge,
                modifier = Modifier.padding(start = 4.dp)
            )

            Spacer(modifier = Modifier.width(8.dp))

            IconButton(onClick = { showRangePicker = true }) {
                Icon(
                    imageVector = Icons.Default.DateRange,
                    contentDescription = "Select trip dates"
                )
            }
        }

        OutlinedTextField(
            value = startDate,
            onValueChange = {},
            label = { Text("Start") },
            readOnly = true,
            modifier = Modifier
                .fillMaxWidth()
        )

        OutlinedTextField(
            value = endDate,
            onValueChange = {},
            label = { Text("End") },
            readOnly = true,
            modifier = Modifier
                .fillMaxWidth()
        )

        // lambda for adding a new trip to database through viewmodel
        val lambda : () -> Unit = {
            val dateFormat = SimpleDateFormat("MM/dd/yyyy", Locale.US)

            val parsedStartDate: Date = dateFormat.parse(startDate)!!
            val parsedEndDate: Date = dateFormat.parse(endDate)!!

            viewModel.addTrip(tripName, parsedStartDate, parsedEndDate)

            onGetStarted()
        }

        NavButton("Create Trip", lambda)

    }

    if (showRangePicker) {
        CreateNewTripDateDialog(
            onDateRangeSelected = { range ->
                val formatter = SimpleDateFormat("MM/dd/yyyy", Locale.getDefault())
                startDate = range.first?.let { formatter.format(Date(it)) } ?: ""
                endDate = range.second?.let { formatter.format(Date(it)) } ?: ""
            },
            onDismiss = { showRangePicker = false }
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CreateNewTripDateDialog(
    onDateRangeSelected: (Pair<Long?, Long?>) -> Unit,
    onDismiss: () -> Unit
) {
    val dateRangePickerState = rememberDateRangePickerState()

    DatePickerDialog(
        onDismissRequest = onDismiss,
        confirmButton = {
            TextButton(
                onClick = {
                    onDateRangeSelected(
                        dateRangePickerState.selectedStartDateMillis to
                                dateRangePickerState.selectedEndDateMillis
                    )
                    onDismiss()
                }
            ) {
                Text("OK")
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text("Cancel")
            }
        }
    ) {
        DateRangePicker(
            state = dateRangePickerState,
            title = { Text("Select date range") },
            showModeToggle = false,
            modifier = Modifier
                .fillMaxWidth()
                .height(500.dp)
                .padding(16.dp)
        )
    }
}
