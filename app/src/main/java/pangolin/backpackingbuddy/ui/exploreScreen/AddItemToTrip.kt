package pangolin.backpackingbuddy.ui.exploreScreen

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.PointerIcon.Companion.Text
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.semantics.Role.Companion.Image
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import pangolin.backpackingbuddy.R
import pangolin.backpackingbuddy.data.Trail
import pangolin.backpackingbuddy.data.Trip
import pangolin.backpackingbuddy.ui.sharedComponents.NavButton
import pangolin.backpackingbuddy.ui.sharedComponents.TripNameDisplay

@Composable
fun AddItemToTrip(trip: Trip, trail: Trail, onAddNewItemClick: (Trip) -> Unit) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
                .background(MaterialTheme.colorScheme.surface),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            TripNameDisplay(trip.tripNameId)

            Spacer(modifier = Modifier.size(16.dp))

            Image(
                painter = painterResource(id = trail.photo),
                contentDescription = "Trail Photo",
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp)
            )

            Spacer(modifier = Modifier.size(16.dp))

            Text(text = trail.name)
            Text(text = trail.location)
            if (trail.description != null) {
                Text(
                    text = trail.description,
                    modifier = Modifier.padding(top = 8.dp)
                )
            }

            Spacer(modifier = Modifier.size(16.dp))

            // choose dates from trip
            Text(text = "On:")
            var selectedDate by remember { mutableStateOf("") }
            OutlinedTextField(
                value = selectedDate,
                onValueChange = { selectedDate = it },
                label = { Text("Select Date") },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.size(16.dp))

            var notes by remember { mutableStateOf("") }
            OutlinedTextField(
                value = notes,
                onValueChange = { notes = it },
                label = { Text("Notes") },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.size(24.dp))

            NavButton("Add to trip!", trip, onAddNewItemClick)

        }
}

@Preview
@Composable
fun PreviewAddItemToTrip() {
    AddItemToTrip(Trip("Durango", listOf("A", "B", "C"), listOf("A", "B", "C")), Trail("Haviland Lake Trail", "Durango", R.drawable.havilandlake, "3.8 miles, Moderate"), {})
}