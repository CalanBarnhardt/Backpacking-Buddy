package pangolin.backpackingbuddy.ui.tripOverviewScreen

import android.util.Log
import androidx.compose.foundation.background
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
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import pangolin.backpackingbuddy.R
import pangolin.backpackingbuddy.data.dataEntries.Campsite
import pangolin.backpackingbuddy.ui.sharedComponents.AddButtonIcon
import pangolin.backpackingbuddy.ui.sharedComponents.NavButton
import pangolin.backpackingbuddy.ui.sharedComponents.TripNameDisplay
import pangolin.backpackingbuddy.viewmodel.BackpackingBuddyViewModel
import java.util.UUID
@Composable
fun ExistingTripOverviewScreen(
    viewModel: BackpackingBuddyViewModel,
    tripID: UUID?,
    onExploreClick: () -> Unit,
    onItineraryClick: () -> Unit,
    onAddButtonClick: () -> Unit
) {
    Log.d("TripOverviewScreen", "Showing trip with ID: $tripID")

    val tripName = tripID?.let { viewModel.getNameFromID(it).collectAsState(initial = "") }

    val trails = tripID?.let { viewModel.getTrailsForTrip(it).collectAsState(initial = emptyList()) }
    val campsites = tripID?.let { viewModel.getCampsitesForTrip(it).collectAsState(initial = emptyList()) }

    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.size(60.dp))

        if (tripName != null) {
            TripNameDisplay(tripName.value)
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
                                TrailOverviewItem(trail)
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
                                CampsiteOverviewItem(campsite)
                            }
                        } else {
                            Text("No campsites added yet.", style = MaterialTheme.typography.bodyMedium)
                        }
                    }
                }
            }
        }
    }
}

@Preview
@Composable
fun PreviewExistingTripOverviewScreen () {
    //ExistingTripOverviewScreen(Trip("Durango", listOf("A", "B", "C"), listOf("A", "B", "C")), {}, {}, {})
}