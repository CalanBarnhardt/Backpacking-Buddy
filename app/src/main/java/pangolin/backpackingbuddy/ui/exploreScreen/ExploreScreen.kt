package pangolin.backpackingbuddy.ui.exploreScreen

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.selection.selectableGroup
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Snackbar
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.MapProperties
import com.google.maps.android.compose.MapType
import com.google.maps.android.compose.MapUiSettings
import com.google.maps.android.compose.Marker
import com.google.maps.android.compose.MarkerState
import com.google.maps.android.compose.Polyline
import com.google.maps.android.compose.rememberCameraPositionState
import kotlinx.coroutines.launch
import pangolin.backpackingbuddy.R
import pangolin.backpackingbuddy.data.dataEntries.Campsite
import pangolin.backpackingbuddy.data.dataEntries.Trail
import pangolin.backpackingbuddy.data.datastore.DataStoreManager
import pangolin.backpackingbuddy.viewmodel.BackpackingBuddyViewModel
import pangolin.backpackingbuddy.viewmodel.BackpackingBuddyViewModelFactory

private const val LOG_TAG = "448.ExploreScreen"

@Composable
fun ExploreScreen(
    viewModel: BackpackingBuddyViewModel
) {
    val cameraPosition = rememberCameraPositionState {
        position = CameraPosition.fromLatLngZoom(LatLng(0.0, 0.0), 0f)
    }
    val selectedLatLngState = remember { mutableStateOf<LatLng?>(null) }
    val mapReadyState = remember { mutableStateOf(false) } // for completeness
    val showTrails = remember { mutableStateOf(false) }
    val showCampsites = remember { mutableStateOf(false) }

    val context = LocalContext.current
    val dataStoreManager = remember { DataStoreManager(context) }
    val lifeCycleOwner = LocalLifecycleOwner.current
    val maptypeState = dataStoreManager
        .maptypeFlow
        .collectAsStateWithLifecycle(
            initialValue = "NORMAL",
            lifecycle = lifeCycleOwner.lifecycle
        )
    val locationState = dataStoreManager
        .locationFlow
        .collectAsStateWithLifecycle(
            initialValue = false,
            lifecycle = lifeCycleOwner.lifecycle
        )

    var mapType: MapType = MapType.NORMAL
    if (maptypeState.value == "TERRAIN") {
        mapType = MapType.TERRAIN
    }
    else if (maptypeState.value == "SATELLITE") {
        mapType = MapType.SATELLITE
    }

    val mapProperties = MapProperties(
        mapType = mapType
    )

    // values for showing a trip
    var selectedTrailName by remember { mutableStateOf<String?>(null) }

    // values for showing a campsite
    var selectedCampsite by remember { mutableStateOf<Campsite?>(null) }

    val scope = rememberCoroutineScope()

    val radioOptions = listOf("NORMAL", "TERRAIN", "SATELLITE")
    var selectedOption = remember { mutableStateOf("NORMAL") }

    Column(
        modifier = Modifier.fillMaxHeight()
    ) {
        Spacer(modifier = Modifier.height(18.dp))

        Text(
            text = "Welcome to Explore",
            style = MaterialTheme.typography.headlineMedium,
            color = MaterialTheme.colorScheme.primary,
            modifier = Modifier.align(Alignment.CenterHorizontally)
        )

        Spacer(modifier = Modifier.height(18.dp))

        Button(
            onClick = {
                showTrails.value = true
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)
        ) {
            Text("Search Trails")
        }

        Button(
            onClick = {
                showCampsites.value = true
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)
        ) {
            Text("Search Campsites")
        }
        
        Spacer(modifier = Modifier.height(4.dp))

        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Row(
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Enable Location?",
                    modifier = Modifier
                )

                Spacer(Modifier.padding(10.dp))

                Switch(
                    checked = locationState.value,
                    onCheckedChange = {
                        scope.launch {
                            dataStoreManager.setLocation(!locationState.value)
                        }
                    }
                )
            }
            Row(
                modifier = Modifier.selectableGroup(),
                horizontalArrangement = Arrangement.Center
            ) {
                radioOptions.forEach { text ->
                    Column(
                        modifier = Modifier.padding(4.dp)
                    ) {
                        RadioButton(
                            selected = (text == selectedOption.value),
                            onClick = {
                                Log.d(LOG_TAG, "changing to be $text")
                                selectedOption.value = text
                                scope.launch {
                                    dataStoreManager.setMaptype(text)
                                }
                            }
                        )
                        Text(
                            text = text
                        )
                    }
                }
            }
        }

        Box(
            modifier = Modifier.fillMaxSize()
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
                properties = mapProperties
            ) {
                selectedLatLngState.value?.let { selected ->
                    Marker(
                        state = MarkerState(position = selected),
                        title = "Selected Location",
                        snippet = "${selected.latitude}, ${selected.longitude}"
                    )
                }

                if (showTrails.value) {
                    val trails = viewModel.trailData
                    val error = viewModel.errorMessage

                    var showTripDialog by remember { mutableStateOf(false) }
                    var trailToAdd by remember { mutableStateOf<Trail?>(null) }

                    val latLng = selectedLatLngState.value
                    LaunchedEffect(latLng!!.latitude, latLng.longitude) {
                        viewModel.loadTrails(latLng.latitude, latLng.longitude)
                    }
                    val nodesMap =
                        trails.filter { it.type == "node" && it.lat != null && it.lon != null }
                            .associateBy { it.id }

                    val ways = trails.filter {
                        it.type == "way" && it.nodes != null
                    }

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
                                    Log.d(LOG_TAG, "a trail has been selected")
                                    selectedTrailName = way.tags?.get("name")
                                    trailToAdd = Trail(
                                        name = way.tags?.get("name") ?: "Unnamed Trail",
                                        location = "Lat: %.2f, Lon: %.2f".format(
                                            latLng.latitude,
                                            latLng.longitude
                                        ),
                                        photo = R.drawable.havilandlake, // filler
                                        distance = polylinePoints.size, // placeholder
                                    )
                                }
                            )
                        }
                    }
                }

                if (showCampsites.value) {
                    val campsites = viewModel.campsiteData
                    val error = viewModel.errorMessage

                    var showTripDialog by remember { mutableStateOf(false) }
                    val trips by viewModel.getAllTrips().collectAsState(initial = emptyList())

                    val latLng = selectedLatLngState.value
                    LaunchedEffect(latLng!!.latitude, latLng.longitude) {
                        viewModel.loadCampsites(latLng.latitude, latLng.longitude)
                    }

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
            }

            if (showTrails.value) {
                selectedTrailName?.let { name ->
                    Log.d(LOG_TAG, "showing the selected trail: $name")
                    Snackbar(
                        modifier = Modifier
                            .padding(16.dp)
                            .align(Alignment.BottomCenter),
                        action = {
                            Row {
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

            if (showCampsites.value) {
                selectedCampsite?.let{ campsite ->
                    Snackbar(
                        modifier = Modifier
                            .padding(16.dp)
                            .align(Alignment.BottomCenter),
                        action = {
                            Row {
                                TextButton(onClick = { selectedCampsite = null }) {
                                    Text("Dismiss")
                                }
                            }
                        }
                    ) {
                        Text("Selected Campsite: ${campsite.name}")
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun PreviewExploreScreen() {
//    val context = LocalContext.current
//    val factory = BackpackingBuddyViewModelFactory(context, lifecycleScope)
//    val backpackingBuddyViewModel = ViewModelProvider(context, factory)[factory.getViewModelClass()]
//    ExploreScreen({}, {})
}