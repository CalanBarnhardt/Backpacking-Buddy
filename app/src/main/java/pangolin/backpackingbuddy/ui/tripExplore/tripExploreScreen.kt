package pangolin.backpackingbuddy.ui.tripExplore

import android.util.Log
import android.widget.Toast
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
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Snackbar
import androidx.compose.ui.Modifier
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.ui.Alignment.Companion
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.MapProperties
import com.google.maps.android.compose.MapUiSettings
import com.google.maps.android.compose.Marker
import com.google.maps.android.compose.MarkerState
import com.google.maps.android.compose.Polyline
import com.google.maps.android.compose.rememberCameraPositionState
import pangolin.backpackingbuddy.R
import pangolin.backpackingbuddy.data.dataEntries.Trail
import pangolin.backpackingbuddy.ui.sharedComponents.NavButton
import pangolin.backpackingbuddy.ui.sharedComponents.TripNameDisplay
import pangolin.backpackingbuddy.viewmodel.BackpackingBuddyViewModel
import java.util.UUID


@Composable
fun ExistingTripExploreScreen(
    viewModel: BackpackingBuddyViewModel,
    tripId: UUID?,
    onOverviewClick: () -> Unit,
    onItineraryClick: () -> Unit,
    onAddButtonClick: () -> Unit,
    onHitTrailSearch: (Double, Double) -> Unit,
    onHitCampsiteSearch: (Double, Double) -> Unit){

    val tripName = tripId?.let { viewModel.getNameFromID(it).collectAsState(initial = "") }

    val selectedLatLngState = remember { mutableStateOf<LatLng?>(null) }
    val mapReadyState = remember { mutableStateOf(false) } // for completeness
    val cameraPosition = rememberCameraPositionState {
        position = CameraPosition.fromLatLngZoom(LatLng(0.0, 0.0), 0f)
    }

    Column (modifier = Modifier
        .fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally) {
        Spacer(modifier = Modifier.size(55.dp))

        // trip name display
        tripName?.value?.let { name ->
            TripNameDisplay(name)
        }

        Spacer(modifier = Modifier.size(16.dp))

        // navigation buttons
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {

            NavButton(stringResource(R.string.overview_button), onOverviewClick);
            NavButton(stringResource(R.string.itinerary_button), onItineraryClick);
            NavButton(stringResource(R.string.explore_button), {}, true);
        }

        Spacer(modifier = Modifier.size(24.dp))
        Text("Select a location on the map and hit \"Search Trails\" or \"Search Campsites\" to view trails or campsites in that area!")
        Spacer(modifier = Modifier.size(24.dp))

        Box(
            modifier = Modifier
                .fillMaxWidth(0.9f)
                .height(250.dp)
                .background(MaterialTheme.colorScheme.tertiary),
            contentAlignment = Alignment.Center
        ) {
            GoogleMap(
                modifier = Modifier.fillMaxSize(),
                cameraPositionState = cameraPosition,
                onMapLoaded = { mapReadyState.value = true },
                onMapClick = { latLng ->
                    Log.d("MapClick", "Clicked at: ${latLng.latitude}, ${latLng.longitude}")
                    selectedLatLngState.value = latLng
                },
                uiSettings = MapUiSettings(),
                properties = MapProperties()
            ) {
                selectedLatLngState.value?.let { selected ->
                    Marker(
                        state = MarkerState(position = selected),
                        title = "Selected Location",
                        snippet = "${selected.latitude}, ${selected.longitude}"
                    )
                }
            }
        }

        Spacer(modifier = Modifier.size(24.dp))

        Button(
            onClick = {
                selectedLatLngState.value?.let { latLng ->
                    onHitTrailSearch(latLng.latitude, latLng.longitude)
                }
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Search Trails")
        }

        Spacer(modifier = Modifier.size(24.dp))

        Button(
            onClick = {
                selectedLatLngState.value?.let { latLng ->
                    onHitCampsiteSearch(latLng.latitude, latLng.longitude)
                }
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Search Campsites")
        }

    }
}

//@Preview
//@Composable
//fun PreviewExisitingTripExploreScreen () {
//    //ExistingTripExploreScreen(Trip("Durango", listOf("A", "B", "C"), listOf("A", "B", "C")), {}, {}, {})
//}
