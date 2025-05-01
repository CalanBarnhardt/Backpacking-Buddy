package pangolin.backpackingbuddy.ui.tripExplore

import android.Manifest
import android.annotation.SuppressLint
import android.content.pm.PackageManager
import android.util.Log
import android.app.Activity
import android.content.Context
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.ui.Modifier
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.MapProperties
import com.google.maps.android.compose.MapUiSettings
import com.google.maps.android.compose.Marker
import com.google.maps.android.compose.MarkerState
import com.google.maps.android.compose.rememberCameraPositionState
import pangolin.backpackingbuddy.R
import pangolin.backpackingbuddy.ui.sharedComponents.NavButton
import pangolin.backpackingbuddy.ui.sharedComponents.TripNameDisplay
import pangolin.backpackingbuddy.viewmodel.BackpackingBuddyViewModel
import java.util.UUID
import android.location.Location
import android.os.Looper
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.padding
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.core.app.ActivityCompat
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
@Composable
fun ExistingTripExploreScreen(
    viewModel: BackpackingBuddyViewModel,
    tripId: UUID?,
    onOverviewClick: () -> Unit,
    onItineraryClick: () -> Unit,
    onAddButtonClick: () -> Unit,
    onHitTrailSearch: (Double, Double) -> Unit,
    onHitCampsiteSearch: (Double, Double) -> Unit
) {
    val context = LocalContext.current
    val fusedLocationClient = remember {
        LocationServices.getFusedLocationProviderClient(context)
    }

    val activity = context as Activity

    val selectedLatLngState = remember { mutableStateOf<LatLng?>(null) }
    val mapReadyState = remember { mutableStateOf(false) }
    val cameraPosition = rememberCameraPositionState {
        position = CameraPosition.fromLatLngZoom(LatLng(0.0, 0.0), 0f)
    }

    val permissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestMultiplePermissions()
    ) { permissions ->
        val fineLocationGranted = permissions[Manifest.permission.ACCESS_FINE_LOCATION] == true
        val coarseLocationGranted = permissions[Manifest.permission.ACCESS_COARSE_LOCATION] == true

        if (fineLocationGranted || coarseLocationGranted) {
            Log.d("LocationPermission", "Permission granted after request")
            fetchLocationAndSearchTrail(fusedLocationClient, context, onHitTrailSearch)
        } else {
            Log.d("LocationPermission", "Permission denied after request")
            Toast.makeText(context, "Permission denied, cannot fetch location", Toast.LENGTH_SHORT).show()
        }
    }

    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.size(55.dp))

        tripId?.let { id ->
            val tripName = viewModel.getNameFromID(id).collectAsState(initial = "")
            tripName.value.let { name ->
                if (name != null) {
                    TripNameDisplay(name)
                }
            }
        }

        Spacer(modifier = Modifier.size(16.dp))

        Row(
            modifier = Modifier.fillMaxWidth().padding(12.dp),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            NavButton(stringResource(R.string.overview_button), onOverviewClick)
            NavButton(stringResource(R.string.itinerary_button), onItineraryClick)
            NavButton(stringResource(R.string.explore_button), {}, true)
        }

        Spacer(modifier = Modifier.size(24.dp))
        Text(text = "Select a location on the map or use your current location to search!", textAlign = TextAlign.Center, modifier =
        Modifier.fillMaxWidth())
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
            modifier = Modifier.fillMaxWidth(0.9f)
        ) {
            Text("Search Trails (Selected Location)")
        }

        Spacer(modifier = Modifier.size(12.dp))

        Button(
            onClick = {
                selectedLatLngState.value?.let { latLng ->
                    onHitCampsiteSearch(latLng.latitude, latLng.longitude)
                }
            },
            modifier = Modifier.fillMaxWidth(0.9f)
        ) {
            Text("Search Campsites (Selected Location)")
        }

        Spacer(modifier = Modifier.size(12.dp))

        Button(
            onClick = {
                checkPermissionAndFetchTrailLocation(activity, permissionLauncher, fusedLocationClient, context, onHitTrailSearch)
            },
            modifier = Modifier.fillMaxWidth(0.9f)
        ) {
            Text("Search Trails (My Location)")
        }

        Spacer(modifier = Modifier.size(12.dp))

        Button(
            onClick = {
                checkPermissionAndFetchTrailLocation(activity, permissionLauncher, fusedLocationClient, context, onHitCampsiteSearch)
            },
            modifier = Modifier.fillMaxWidth(0.9f)
        ) {
            Text("Search Campsites (My Location)")
        }
    }
}

private fun checkPermissionAndFetchTrailLocation(
    activity: Activity,
    permissionLauncher: ActivityResultLauncher<Array<String>>,
    fusedLocationClient: FusedLocationProviderClient,
    context: Context,
    onLocationResult: (Double, Double) -> Unit
) {
    if (ActivityCompat.checkSelfPermission(activity, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED ||
        ActivityCompat.checkSelfPermission(activity, Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED) {

        Log.d("LocationUtility", "Permission already granted")
        fetchLocationAndSearchTrail(fusedLocationClient, context, onLocationResult)

    } else {
        if (ActivityCompat.shouldShowRequestPermissionRationale(activity, Manifest.permission.ACCESS_FINE_LOCATION) ||
            ActivityCompat.shouldShowRequestPermissionRationale(activity, Manifest.permission.ACCESS_COARSE_LOCATION)) {

            Log.d("LocationUtility", "Permission previously denied")
            Toast.makeText(activity, "We need your location to find nearby trails or campsites.", Toast.LENGTH_LONG).show()

        } else {
            Log.d("LocationUtility", "Requesting location permission")
            permissionLauncher.launch(arrayOf(
                Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.ACCESS_COARSE_LOCATION
            ))
        }
    }
}

@SuppressLint("MissingPermission")
private fun fetchLocationAndSearchTrail(
    fusedLocationClient: FusedLocationProviderClient,
    context: Context,
    onLocationResult: (Double, Double) -> Unit
) {
    fusedLocationClient.lastLocation.addOnSuccessListener { location: Location? ->
        location?.let {
            onLocationResult(it.latitude, it.longitude)
        } ?: run {
            Toast.makeText(context, "Unable to retrieve location.", Toast.LENGTH_SHORT).show()
        }
    }
}


//@Preview
//@Composable
//fun PreviewExisitingTripExploreScreen () {
//    //ExistingTripExploreScreen(Trip("Durango", listOf("A", "B", "C"), listOf("A", "B", "C")), {}, {}, {})
//}
