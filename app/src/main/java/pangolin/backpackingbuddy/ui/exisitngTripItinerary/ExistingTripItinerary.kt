package pangolin.backpackingbuddy.ui.exisitngTripItinerary

import androidx.compose.foundation.background
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
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import pangolin.backpackingbuddy.R
import pangolin.backpackingbuddy.data.Trip
import pangolin.backpackingbuddy.ui.sharedComponents.NavButton
import pangolin.backpackingbuddy.ui.sharedComponents.TripNameDisplay
import androidx.compose.material3.Text
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.draw.clip
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import pangolin.backpackingbuddy.ui.existingTripExplore.ExistingTripExploreScreen
import pangolin.backpackingbuddy.ui.existingTripOverviewScreen.OverviewDropdown
import pangolin.backpackingbuddy.ui.existingTripOverviewScreen.OverviewForEach


@Composable
fun ExisitngTripItinerary (trip : Trip, ) {
    Column (modifier = Modifier
        .fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally) {

        // trip name display
        TripNameDisplay(trip.tripNameId)
        Spacer(modifier = Modifier.size(16.dp))

        // map display
        Text (text = "Insert Map Here")
        Spacer(modifier = Modifier.size(16.dp))

        // button display
        Row {
            NavButton(stringResource(R.string.overview_button))
            NavButton(stringResource(R.string.explore_button))
            NavButton(stringResource(R.string.itinerary_button))
        }
        Spacer(modifier = Modifier.size(16.dp))
        Box (modifier = Modifier
            .fillMaxWidth(0.9f)
            .height(300.dp)
            .clip(RoundedCornerShape(12.dp))
            .background(MaterialTheme.colorScheme.background))
        {
            val accessingDate = remember { mutableStateOf<String>("6/28") }
            val dates = listOf("6/28", "6/29", "6/30")
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp)
            ) {
                item {
                    Column {
                        //list dates
                        Row (modifier = Modifier
                            .fillMaxWidth()
                            .padding(end = 5.dp))   {
                            dates.forEach { date ->
                                Text(text = date)
                            }
                        }
                        //list information for date
                        Text(text = "List information about trail and campsites here")
                    }

                }
            }
        }
    }
}

@Preview
@Composable
fun previewExistingTripItinerary(){
    ExisitngTripItinerary(Trip("Durango", listOf("A", "B", "C"), listOf("A", "B", "C")))

}
