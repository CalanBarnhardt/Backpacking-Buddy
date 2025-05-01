package pangolin.backpackingbuddy.ui.tripOverviewScreen

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import pangolin.backpackingbuddy.R
import pangolin.backpackingbuddy.data.dataEntries.Campsite
import pangolin.backpackingbuddy.data.dataEntries.Trail
import pangolin.backpackingbuddy.ui.sharedComponents.NavButton
import pangolin.backpackingbuddy.ui.sharedComponents.TripNameDisplay
import pangolin.backpackingbuddy.viewmodel.BackpackingBuddyViewModel
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale
import java.util.UUID

@Composable
fun ExistingTripOverviewScreen(
    viewModel: BackpackingBuddyViewModel,
    tripID: UUID?,
    onExploreClick: () -> Unit,
    onItineraryClick: () -> Unit,
    onAddButtonClick: () -> Unit,
) {
    Log.d("TripOverviewScreen", "Showing trip with ID: $tripID")
    var showCampsitePrompt by remember { mutableStateOf(false) }
    var selectedCampsite by remember { mutableStateOf<Campsite?>(null) }

    var showTrailPrompt by remember { mutableStateOf(false) }
    var selectedTrail by remember { mutableStateOf<Trail?>(null) }

    val tripDates = tripID?.let { viewModel.getTripDates(it).collectAsState(initial = null) }
    val dateFormatter = remember { SimpleDateFormat("MM/dd", Locale.getDefault()) }

    val dates = remember(tripDates?.value) {
        tripDates?.value?.let { (start, end) ->
            val list = mutableListOf<String>()
            val calendar = Calendar.getInstance().apply { time = start }
            val endCalendar = Calendar.getInstance().apply { time = end }

            while (!calendar.after(endCalendar)) {
                list.add(dateFormatter.format(calendar.time))
                calendar.add(Calendar.DATE, 1)
            }

            list
        } ?: emptyList()
    }

    val tripName = tripID?.let { viewModel.getNameFromID(it).collectAsState(initial = "") }

    val trails = tripID?.let { viewModel.getTrailsForTrip(it).collectAsState(initial = emptyList()) }
    val campsites = tripID?.let { viewModel.getCampsitesForTrip(it).collectAsState(initial = emptyList()) }

    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.size(60.dp))

        tripName?.value?.let { name ->
            TripNameDisplay(name)
        }

        Spacer(modifier = Modifier.size(16.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            NavButton(stringResource(R.string.overview_button), {}, true)
            if (tripID != null) {
                NavButton(stringResource(R.string.itinerary_button), onItineraryClick)
                NavButton(stringResource(R.string.explore_button), onExploreClick)
            }
        }

        Spacer(modifier = Modifier.size(16.dp))

        Box(
            modifier = Modifier
                .fillMaxWidth(0.9f)
                .height(400.dp)
                .clip(RoundedCornerShape(12.dp))
                .background(MaterialTheme.colorScheme.surface)
        ) {
            val trailsExpanded = remember { mutableStateOf(true) }
            val campsiteExpanded = remember { mutableStateOf(true) }

            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp)
            ) {
                item {
                    // Trails dropdown
                    OverviewDropdown(
                        title = stringResource(R.string.trail_text),
                        expanded = trailsExpanded.value,
                        onToggle = { trailsExpanded.value = !trailsExpanded.value }
                    )

                    if (trailsExpanded.value) {
                        if (trails != null && trails.value.isNotEmpty()) {
                            trails.value.forEach { trail ->
                                if (trail != null) {
                                    TrailOverviewItem(trail,
                                        { showTrailPrompt = true
                                            selectedTrail = trail })
                                }
                            }
                        } else {
                            Text("No trails added yet.", style = MaterialTheme.typography.bodyMedium)
                        }
                    }

                    Spacer(modifier = Modifier.size(16.dp))

                    // Campsites dropdown
                    OverviewDropdown(
                        title = stringResource(R.string.campsite_text),
                        expanded = campsiteExpanded.value,
                        onToggle = { campsiteExpanded.value = !campsiteExpanded.value }
                    )

                    if (campsiteExpanded.value) {
                        if (campsites != null && campsites.value.isNotEmpty()) {
                            campsites.value.forEach { campsite ->
                                if (campsite != null) {
                                    CampsiteOverviewItem(
                                        campsite,
                                        { showCampsitePrompt = true
                                            selectedCampsite = campsite })
                                }
                            }
                        } else {
                            Text("No campsites added yet.", style = MaterialTheme.typography.bodyMedium)
                        }
                    }
                }
            }
        }
        if(showCampsitePrompt) {
            CampsiteDialog(
                dates,
                onDateSelected = { date ->
                    showCampsitePrompt = false
                    selectedCampsite?.let { viewModel.associateCampsiteWithDate(it, date) }
                                 },
                onDeleteClick = {
                    showCampsitePrompt = false
                    selectedCampsite?.let { viewModel.deleteCampsite(it) }

                },
                onDismiss = {
                    showCampsitePrompt = false
                }
                    )
        }
        if(showTrailPrompt) {
            CampsiteDialog(
                dates,
                onDateSelected = { date ->
                    showTrailPrompt = false
                    selectedTrail?.let { viewModel.associateTrailWithDate(it, date) }
                },
                onDeleteClick = {
                    showTrailPrompt = false
                    selectedTrail?.let { viewModel.deleteTrail(it) }
                },
                onDismiss = {
                    showTrailPrompt = false
                }
            )
        }
    }
}

@Composable
fun CampsiteDialog(
    dates: List<String>,
    onDateSelected: (String) -> Unit,
    onDeleteClick: () -> Unit,
    onDismiss: () -> Unit
) {
    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("Select an Item") },
        text = {
            Column {
                dates.forEach { item ->
                    Text(
                        text = item,
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable {
                                onDateSelected(item)
                                onDismiss()
                            }
                            .padding(vertical = 8.dp)
                    )
                }

                Spacer(modifier = Modifier.height(12.dp))

                Divider()

                // Delete button row
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable {
                            onDeleteClick()
                            onDismiss()
                        }
                        .padding(vertical = 12.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        imageVector = Icons.Default.Delete,
                        contentDescription = "Delete",
                        tint = Color.Red,
                        modifier = Modifier.padding(end = 8.dp)
                    )
                    Text("Delete", color = Color.Red)
                }
            }
        },
        confirmButton = {
            TextButton(onClick = onDismiss) {
                Text("Cancel")
            }
        }
    )
}



@Preview
@Composable
fun PreviewExistingTripOverviewScreen () {
    //ExistingTripOverviewScreen(Trip("Durango", listOf("A", "B", "C"), listOf("A", "B", "C")), {}, {}, {})
}