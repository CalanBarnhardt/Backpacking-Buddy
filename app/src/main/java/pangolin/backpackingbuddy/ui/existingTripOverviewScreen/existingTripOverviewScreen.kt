package pangolin.backpackingbuddy.ui.existingTripOverviewScreen

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
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.PointerIcon.Companion.Text
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import pangolin.backpackingbuddy.R
import pangolin.backpackingbuddy.data.Trip
import pangolin.backpackingbuddy.ui.sharedComponents.AddButtonIcon
import pangolin.backpackingbuddy.ui.sharedComponents.BulletPoint
import pangolin.backpackingbuddy.ui.sharedComponents.ExpandableIcon
import pangolin.backpackingbuddy.ui.sharedComponents.NavButton
import pangolin.backpackingbuddy.ui.sharedComponents.TripNameDisplay

@Composable
fun ExistingTripOverviewScreen (
    trip : Trip,
    onExploreClick: (Trip) -> Unit,
    onItineraryClick: (Trip) -> Unit) {
    Log.d("TripOverviewScreen", "Showing trip with ID: $trip.tripId")
    Column (modifier=
        Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally)    {
        Spacer(modifier = Modifier.size(60.dp))

        //trip title text header
        TripNameDisplay(trip.tripNameId)

        Spacer(modifier = Modifier.size(16.dp))

        // navigation buttons
        Row (
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly) {

            NavButton(stringResource(R.string.overview_button), trip);
            NavButton(stringResource(R.string.itinerary_button), trip, onItineraryClick);
            NavButton(stringResource(R.string.explore_button), trip, onExploreClick);
        }

        Spacer(modifier = Modifier.size(16.dp))

        // display of trails and campsites for given trip
        Box (modifier = Modifier
            .fillMaxWidth(0.9f)
            .height(300.dp)
            .clip(RoundedCornerShape(12.dp))
            .background(MaterialTheme.colorScheme.background))
            {
            val trailsExpanded = remember { mutableStateOf<Boolean>(true) }
            val campsiteExpanded = remember { mutableStateOf<Boolean>(true) }
            LazyColumn (modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)) {
                item {
                    // trails dropdown
                    trailsExpanded.value = OverviewDropdown(trailsExpanded.value, trip, stringResource(R.string.trail_text))
                    //content for trails
                    if (trailsExpanded.value) {
                        trip.trails.forEach { trail ->
                            OverviewForEach(trail)
                        }

                        Spacer(modifier = Modifier.size(16.dp))

                        // campsites dropdown
                        campsiteExpanded.value = OverviewDropdown(campsiteExpanded.value, trip, stringResource(R.string.campsite_text))
                        //content for trails
                        if (trailsExpanded.value) {
                            trip.campsites.forEach { campsite ->
                                OverviewForEach(campsite)
                            }
                        }
                    }
                }
            }
            Row (modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(5.dp)) {
                AddButtonIcon()
            }
        }
    }
}

@Preview
@Composable
fun PreviewExistingTripOverviewScreen () {
    ExistingTripOverviewScreen(Trip("Durango", listOf("A", "B", "C"), listOf("A", "B", "C")), {}, {})
}