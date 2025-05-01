package pangolin.backpackingbuddy.ui

import android.widget.Toast
import androidx.compose.runtime.*
import androidx.compose.material3.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.*
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
import pangolin.backpackingbuddy.data.dataEntries.Trail
import pangolin.backpackingbuddy.viewmodel.BackpackingBuddyViewModel

@Composable
fun TrailScreen(viewModel: BackpackingBuddyViewModel, lat: Double, lon: Double) {
    val trails = viewModel.trailData
    val error = viewModel.errorMessage
    val context = LocalContext.current

    var showTripDialog by remember { mutableStateOf(false) }
    var trailToAdd by remember { mutableStateOf<Trail?>(null) }

    val cameraPositionState = rememberCameraPositionState {
        position = CameraPosition.fromLatLngZoom(LatLng(lat, lon), 13f)
    }

    var selectedTrailName by remember { mutableStateOf<String?>(null) }

    LaunchedEffect(lat, lon) {
        viewModel.loadTrails(lat, lon)
    }

    val nodesMap = trails.filter { it.type == "node" && it.lat != null && it.lon != null }
        .associateBy { it.id }

    val ways = trails.filter {
        it.type == "way" && it.nodes != null
    }

    Box(modifier = Modifier.fillMaxSize()) {
        GoogleMap(
            modifier = Modifier.fillMaxSize(),
            cameraPositionState = cameraPositionState
        ) {
            ways.forEach { way ->
                val polylinePoints = way.nodes!!.mapNotNull { nodeId ->
                    nodesMap[nodeId]?.let { LatLng(it.lat!!, it.lon!!) }
                }

                if (polylinePoints.size >= 2) {
                    Polyline(
                        points = polylinePoints,
                        clickable = true,
                        color = Color.Blue,
                        width = 8f,
                        onClick = {
                            selectedTrailName = way.tags?.get("name")
                            trailToAdd = Trail(
                                name = way.tags?.get("name") ?: "Unnamed Trail",
                                location = "Lat: %.2f, Lon: %.2f".format(lat, lon),
                                photo = R.drawable.havilandlake, // filler
                                distance = polylinePoints.size, // placeholder
                            )
                        }
                    )
                }
            }
        }

        selectedTrailName?.let { name ->
            Snackbar(
                modifier = Modifier
                    .padding(16.dp)
                    .align(Alignment.BottomCenter),
                action = {
                    Row {
                        TextButton(onClick = { showTripDialog = true }) {
                            Text("Add to Trip")
                        }
                        TextButton(onClick = { selectedTrailName = null }) {
                            Text("Dismiss")
                        }
                    }
                }
            ) {
                Text("Selected Trail: $name")
            }
        }
    }

    if (showTripDialog && trailToAdd != null) {
        val trips by viewModel.getAllTrips().collectAsState(initial = emptyList())

        AlertDialog(
            onDismissRequest = { showTripDialog = false },
            title = { Text("Add to which trip?") },
            text = {
                Column {
                    trips.forEach { trip ->
                        TextButton(onClick = {
                            if (trip != null) {
                                viewModel.addTrailToTrip(trailToAdd!!, trip.trip_id)
                            }
                            if (trip != null) {
                                Toast.makeText(
                                    context,
                                    "${trailToAdd!!.name} added to ${trip.trip_name}",
                                    Toast.LENGTH_SHORT
                                ).show()
                            }
                            showTripDialog = false
                            selectedTrailName = null
                        }) {
                            if (trip != null) {
                                Text(trip.trip_name)
                            }
                        }
                    }
                }
            },
            confirmButton = {},
            dismissButton = {
                TextButton(onClick = { showTripDialog = false }) {
                    Text("Cancel")
                }
            }
        )
    }


    if (error != null) {
        Text(
            text = "Error: $error",
            color = MaterialTheme.colorScheme.error,
            modifier = Modifier.padding(16.dp)
        )
    }
}

