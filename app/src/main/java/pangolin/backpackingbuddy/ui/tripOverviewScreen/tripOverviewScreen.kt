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
import pangolin.backpackingbuddy.ui.sharedComponents.AddButtonIcon
import pangolin.backpackingbuddy.ui.sharedComponents.NavButton
import pangolin.backpackingbuddy.ui.sharedComponents.TripNameDisplay
import pangolin.backpackingbuddy.viewmodel.BackpackingBuddyViewModel
import java.util.UUID

@Composable
fun ExistingTripOverviewScreen (
    viewModel: BackpackingBuddyViewModel,
    tripID: UUID?,
    onExploreClick: () -> Unit,
    onItineraryClick: () -> Unit,
    onAddButtonClick: () -> Unit) {
    Log.d("TripOverviewScreen", "Showing trip with ID: $tripID")

    // retrieve name from trip ID
    val tripName = tripID?.let { viewModel.getNameFromID(it).collectAsState(initial="") }

    // TODO: retrieve trails from trip ID

    Column (modifier=
        Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally)    {
        Spacer(modifier = Modifier.size(60.dp))

        //trip title text header
        if (tripName != null) {
            TripNameDisplay(tripName.value)
        }

        Spacer(modifier = Modifier.size(16.dp))

        // navigation buttons
        Row (
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly) {

            NavButton(stringResource(R.string.overview_button), {}, true);
            if (tripID != null) {
                NavButton(stringResource(R.string.itinerary_button), onItineraryClick)
            };
            if (tripID != null) {
                NavButton(stringResource(R.string.explore_button), onExploreClick)
            };
        }

        Spacer(modifier = Modifier.size(16.dp))

        // display of trails and campsites for given trip
        Box (modifier = Modifier
            .fillMaxWidth(0.9f)
            .height(300.dp)
            .clip(RoundedCornerShape(12.dp))
            .background(MaterialTheme.colorScheme.surface),
        ){
            // TODO: retrieve trails related to tripID
            val trailsExpanded = remember { mutableStateOf<Boolean>(true) }

            // TODO: retrieve campsites related to tripID
            val campsiteExpanded = remember { mutableStateOf<Boolean>(true) }

            // TODO: update logic to display trails/campsites
            LazyColumn (modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)) {
                item {
                    // trails dropdown
                    trailsExpanded.value = OverviewDropdown(trailsExpanded.value, stringResource(R.string.trail_text))
                    //content for trails
//                    if (trailsExpanded.value) {
//                        trip.trails.forEach { trail ->
//                            OverviewForEach(trail)
//                        }
//                        OverviewForEach("Island Lake")
//                        OverviewForEach("Haviland Lake Trail")
//                        OverviewForEach("Rifle Falls")
//                    }


//                        Spacer(modifier = Modifier.size(16.dp))
//
//                        // campsites dropdown
//                        campsiteExpanded.value = OverviewDropdown(campsiteExpanded.value, stringResource(R.string.campsite_text))
//                        //content for trails
//                        if (trailsExpanded.value) {
//                            trip.campsites.forEach { campsite ->
//                                OverviewForEach(campsite)
//                            }
//                            OverviewForEach("Bear Campground")
//                            OverviewForEach("Aspen Campground")
//                        }

                }
            }
            Row (modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(5.dp)) {
                AddButtonIcon(onAddButtonClick, MaterialTheme.colorScheme.onPrimaryContainer)
            }
        }
    }
}

@Preview
@Composable
fun PreviewExistingTripOverviewScreen () {
    //ExistingTripOverviewScreen(Trip("Durango", listOf("A", "B", "C"), listOf("A", "B", "C")), {}, {}, {})
}