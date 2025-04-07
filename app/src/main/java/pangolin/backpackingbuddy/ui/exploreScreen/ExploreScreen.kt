package pangolin.backpackingbuddy.ui.exploreScreen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.rememberCameraPositionState
import pangolin.backpackingbuddy.ui.sharedComponents.SearchBar

@Composable
fun ExploreScreen(onHitSearch: () -> Unit) {
    val cameraPosition = rememberCameraPositionState {
        position = CameraPosition.fromLatLngZoom(LatLng(0.0, 0.0), 0f)
    }
    Column(
        modifier = Modifier.fillMaxHeight()
    ) {
        Spacer(modifier = Modifier.height(32.dp))

        Text(
            text = "Welcome to Explore",
            style = MaterialTheme.typography.headlineMedium,
            color = MaterialTheme.colorScheme.primary
        )

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = "Searching is not yet implemented, but you can view Front Range trails by hitting “Search Trails” below.",
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            lineHeight = 20.sp
        )

        Spacer(modifier = Modifier.height(32.dp))

        Row(
            modifier = Modifier.fillMaxWidth()
        ) {
            SearchBar() // search bar will hold trails and campsites
        }

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = { onHitSearch() },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Search Trails")
        }
        
        Spacer(modifier = Modifier.height(16.dp))

        GoogleMap(
            modifier = Modifier.fillMaxSize(),
            cameraPositionState = cameraPosition
        ) {
            // TODO - will need to have a location/locations that are nullable for if a trail is selected
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun PreviewExploreScreen() {
    ExploreScreen({})
}