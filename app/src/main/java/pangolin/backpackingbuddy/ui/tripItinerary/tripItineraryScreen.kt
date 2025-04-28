package pangolin.backpackingbuddy.ui.tripItinerary

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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import pangolin.backpackingbuddy.R
import pangolin.backpackingbuddy.ui.sharedComponents.NavButton
import pangolin.backpackingbuddy.ui.sharedComponents.TripNameDisplay
import androidx.compose.material3.Text
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.rememberCameraPositionState
import pangolin.backpackingbuddy.viewmodel.BackpackingBuddyViewModel
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale
import java.util.UUID
import com.google.accompanist.flowlayout.FlowRow
import pangolin.backpackingbuddy.data.dataEntries.Campsite
import pangolin.backpackingbuddy.data.dataEntries.Trail
import pangolin.backpackingbuddy.ui.tripOverviewScreen.CampsiteOverviewItem
import pangolin.backpackingbuddy.ui.tripOverviewScreen.TrailOverviewItem

@Composable
fun ExisitngTripItinerary (
    viewModel: BackpackingBuddyViewModel,
    tripId : UUID?,
    onOverviewClick: () -> Unit,
    onExploreClick: () -> Unit ) {
    val cameraPosition = rememberCameraPositionState {
        position = CameraPosition.fromLatLngZoom(LatLng(38.1, -104.1), 12f)
    }

    val tripName = tripId?.let { viewModel.getNameFromID(it).collectAsState(initial = "") }

    Column (modifier = Modifier
        .fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally) {

        Spacer(modifier = Modifier.size(55.dp))
        // trip name display
        tripName?.value?.let { name ->
            TripNameDisplay(name)
        }

        Spacer(modifier = Modifier.size(16.dp))

        // button display
        Row (modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly) {
            NavButton(stringResource(R.string.overview_button) , onOverviewClick)
            NavButton(stringResource(R.string.itinerary_button), {}, true)
            NavButton(stringResource(R.string.explore_button), onExploreClick)

        }
        Spacer(modifier = Modifier.size(16.dp))

        // map display
        Box(
            modifier = Modifier
                .fillMaxWidth(0.9f)
                .height(200.dp)
                .background(MaterialTheme.colorScheme.tertiary),
            contentAlignment = Alignment.Center
        ) {
            GoogleMap(
                modifier = Modifier.fillMaxSize(),
                cameraPositionState = cameraPosition
            ) {
                // TODO - will need to have a location/locations associated with each trip to have markers
            }
        }
        Spacer(modifier = Modifier.size(16.dp))

        // dates display
        Spacer(modifier = Modifier.size(16.dp))
        Box (modifier = Modifier
            .fillMaxWidth(0.9f)
            .height(300.dp)
            .clip(RoundedCornerShape(12.dp))
            .background(MaterialTheme.colorScheme.surface))
        {
            // getting trip date list
            //=============================================
            val tripDates = tripId?.let { viewModel.getTripDates(it).collectAsState(initial = null) }
            if (tripDates != null) {
                if (tripDates.value == null){
                    Log.d("tag", "trip dates is null")
                } else {
                    Log.d("tag", "trip dates is not null")
                }
            }

            val dateFormatter = remember { SimpleDateFormat("MM/dd", Locale.getDefault()) }

            val dates = remember(tripDates?.value) {
                tripDates?.value?.let { (start, end) ->
                    Log.d("log", start.toString() )
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
            var selectedDate by remember { mutableStateOf(dates.firstOrNull()) }


            //==================================================================================

            // collect campsites and trails
            val trails = tripId?.let { viewModel.getTrailsForTrip(it).collectAsState(initial = emptyList()) }
            val campsites = tripId?.let { viewModel.getCampsitesForTrip(it).collectAsState(initial = emptyList()) }

            // create a map to associate trips and trails
            val campsiteMap: Map<String, List<Campsite>> = dates.associateWith { date ->
                campsites?.value?.filter { it.date == date } ?: emptyList()
            }

            val trailsMap: Map<String, List<Trail>> = dates.associateWith { date ->
                trails?.value?.filter { it.date == date } ?: emptyList()
            }

            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp)
            ) {
                item {
                    Column {
                        Text(text = "Trip Dates:")
                        Spacer(modifier = Modifier.size(8.dp))

                        FlowRow(
                            mainAxisSpacing = 8.dp,
                            crossAxisSpacing = 8.dp,
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(end = 5.dp)
                        ) {
                            dates.forEach { date ->
                                Text(
                                    modifier = Modifier.clickable {
                                        selectedDate = date
                                    },
                                    text = date,
                                    style = MaterialTheme.typography.bodyMedium
                                )
                            }
                        }

                        Spacer(modifier = Modifier.size(24.dp))
                        val trailsForSelectedDate = trailsMap[selectedDate] ?: emptyList()
                        trailsForSelectedDate.forEach { trail ->
                            TrailOverviewItem(
                                trail,
                                { })
                        }
                        val campsitesForSelectedDate = campsiteMap[selectedDate] ?: emptyList()
                        campsitesForSelectedDate.forEach { campsite ->
                            CampsiteOverviewItem(
                                campsite,
                                { })
                        }

                    }
                }
            }
        }
    }
}

@Preview
@Composable
fun previewExistingTripItinerary(){
    //ExisitngTripItinerary(Trip("Durango", listOf("A", "B", "C"), listOf("A", "B", "C")), {}, {})

}
