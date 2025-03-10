package pangolin.backpackingbuddy.ui.existingTripExplore

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import pangolin.backpackingbuddy.R
import pangolin.backpackingbuddy.data.Trail
import pangolin.backpackingbuddy.data.Trip
import pangolin.backpackingbuddy.ui.sharedComponents.AddButtonIcon
import pangolin.backpackingbuddy.ui.sharedComponents.BulletPoint
import pangolin.backpackingbuddy.ui.sharedComponents.NavButton
import pangolin.backpackingbuddy.ui.sharedComponents.SearchBar
import pangolin.backpackingbuddy.ui.sharedComponents.TripNameDisplay


@Composable
fun ExistingTripExploreScreen(
    trip: Trip,
    onOverviewClick: (Trip) -> Unit,
    onItineraryClick: (Trip) -> Unit,
    onAddButtonClick: () -> Unit) {

    val searchResults = listOf(Trail("Island Lake", "Durango", R.drawable.islandlake ), Trail("Haviland Lake Trail", "Durango", R.drawable.havilandlake, "3.8 miles, Moderate"))

    Column (modifier = Modifier
        .fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally){
        Spacer(modifier = Modifier.size(55.dp))
        // trip name display
        TripNameDisplay(trip.tripNameId)

        Spacer(modifier = Modifier.size(16.dp))

        // navigation buttons
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {

            NavButton(stringResource(R.string.overview_button), trip, onOverviewClick);
            NavButton(stringResource(R.string.itinerary_button), trip, onItineraryClick);
            NavButton(stringResource(R.string.explore_button), trip);
        }

        // search bar
        SearchBar()

        // trail cards
        LazyColumn {
            item {
                searchResults.forEach { trail ->
                    Box (modifier = Modifier
                        .fillMaxWidth()
                        .padding(top=16.dp)) {

                        Box (modifier=Modifier
                            .offset((-30.dp), (3.dp))
                            .align(Alignment.TopEnd)
                            .zIndex(1f))
                            { AddButtonIcon(onAddButtonClick) }

                        Card (modifier = Modifier
                            .fillMaxWidth(0.8f)
                            .padding(top=16.dp)
                            .align(Alignment.Center)) {
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()

                            ) {
                                Column(
                                    modifier = Modifier
                                        .fillMaxHeight()
                                        .padding(5.dp)
                                        .padding(top = 5.dp)
                                        .weight(1f)
                                ) {

                                    Text(
                                        text = trail.name
                                    )
                                    Row(
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .padding(5.dp)
                                    ) {
                                        BulletPoint()
                                        Text(
                                            text = trail.location
                                        )

                                    }
                                    if (trail.description != null) {
                                        Text(
                                            text = trail.description,
                                            modifier = Modifier
                                                .fillMaxWidth()
                                                .padding(start = 8.dp)
                                                .padding(bottom = 5.dp)
                                        )
                                    }
                                }
                                Image(
                                    painter = painterResource(id = trail.photo),
                                    contentDescription = "Trail Photo",
                                    modifier = Modifier
                                        .fillMaxWidth(0.4f)
                                        .padding(top = 10.dp)
                                        .padding(bottom = 10.dp)
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}

@Preview
@Composable
fun PreviewExisitingTripExploreScreen () {
    ExistingTripExploreScreen(Trip("Durango", listOf("A", "B", "C"), listOf("A", "B", "C")), {}, {}, {})
}
