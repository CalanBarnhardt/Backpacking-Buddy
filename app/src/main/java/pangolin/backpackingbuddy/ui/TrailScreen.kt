package pangolin.backpackingbuddy.ui

import androidx.compose.runtime.*
import androidx.compose.material3.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.Marker
import com.google.maps.android.compose.MarkerState
import com.google.maps.android.compose.Polyline
import com.google.maps.android.compose.rememberCameraPositionState
import pangolin.backpackingbuddy.viewmodel.BackpackingBuddyViewModel

@Composable
fun TrailScreen(viewModel: BackpackingBuddyViewModel) {
    val trails = viewModel.trailData
    val error = viewModel.errorMessage

    val cameraPositionState = rememberCameraPositionState {
        position = CameraPosition.fromLatLngZoom(LatLng(39.755, -105.221), 13f)
    }

    var selectedTrailName by remember { mutableStateOf<String?>(null) }

    LaunchedEffect(Unit) {
        viewModel.loadTrails()
    }

    val nodesMap = trails.filter { it.type == "node" && it.lat != null && it.lon != null }
        .associateBy { it.id }

    val ways = trails.filter {
        it.type == "way" && it.nodes != null && it.tags?.containsKey("name") == true
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
                        }
                    )
                }
            }
        }

        // TODO: add button "add trail to trip"
        selectedTrailName?.let { name ->
            Snackbar(
                modifier = Modifier
                    .padding(16.dp)
                    .align(Alignment.BottomCenter),
                action = {
                    TextButton(onClick = { selectedTrailName = null }) {
                        Text("Dismiss")
                    }
                }
            ) {
                Text("Selected Trail: $name")
            }
        }
    }

    if (error != null) {
        Text(
            text = "Error: $error",
            color = MaterialTheme.colorScheme.error,
            modifier = Modifier.padding(16.dp)
        )
    }
}

