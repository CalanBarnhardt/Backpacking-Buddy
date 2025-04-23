package pangolin.backpackingbuddy.ui

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Snackbar
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.Marker
import com.google.maps.android.compose.MarkerState
import com.google.maps.android.compose.Polyline
import com.google.maps.android.compose.rememberCameraPositionState
import pangolin.backpackingbuddy.R
import pangolin.backpackingbuddy.data.dataEntries.Campsite
import pangolin.backpackingbuddy.data.dataEntries.Trail
import pangolin.backpackingbuddy.ui.navigation.specs.CampsiteScreenSpec

import pangolin.backpackingbuddy.viewmodel.BackpackingBuddyViewModel


@Composable
fun CampsiteScreen(viewModel: BackpackingBuddyViewModel, lat: Double, lon: Double) {
    val campsites = viewModel.campsiteData
    val error = viewModel.errorMessage
    val context = LocalContext.current

    val cameraPositionState = rememberCameraPositionState {
        position = CameraPosition.fromLatLngZoom(LatLng(lat, lon), 10f)
    }

    var showTripDialog by remember { mutableStateOf(false) }
    var selectedCampsite by remember { mutableStateOf<Campsite?>(null) }

    val trips by viewModel.getAllTrips().collectAsState(initial = emptyList())

    LaunchedEffect(lat, lon) {
        viewModel.loadCampsites(lat, lon)
    }

    Box(modifier = Modifier.fillMaxSize()) {
        GoogleMap(
            modifier = Modifier.fillMaxSize(),
            cameraPositionState = cameraPositionState
        ) {
            campsites.filter { it.lat != null && it.lon != null }.forEach { elem ->
                val name = elem.tags?.get("name") ?: "Unnamed campsite"
                Marker(
                    state = MarkerState(position = LatLng(elem.lat!!, elem.lon!!)),
                    title = name,
                    onClick = {
                        selectedCampsite = Campsite(
                            name = name,
                            lat = elem.lat,
                            lon = elem.lon,
                        )
                        showTripDialog = true
                        true
                    }
                )
            }
        }

        // Dialog to select a trip to save the campsite
        if (showTripDialog && selectedCampsite != null) {
            AlertDialog(
                onDismissRequest = { showTripDialog = false },
                title = { Text("Add ${selectedCampsite!!.name} to Trip") },
                text = {
                    Column {
                        trips.forEach { trip ->
                            TextButton(onClick = {
                                viewModel.addCampsiteToTrip(selectedCampsite!!, trip.trip_id)
                                Toast.makeText(
                                    context,
                                    "${selectedCampsite!!.name} added to ${trip.trip_name}",
                                    Toast.LENGTH_SHORT
                                ).show()
                                showTripDialog = false
                                selectedCampsite = null
                            }) {
                                Text(trip.trip_name)
                            }
                        }
                    }
                },
                confirmButton = {},
                dismissButton = {
                    TextButton(onClick = {
                        showTripDialog = false
                        selectedCampsite = null
                    }) {
                        Text("Cancel")
                    }
                }
            )
        }
    }
}
