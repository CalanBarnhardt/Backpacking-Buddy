package pangolin.backpackingbuddy.ui.tripOverviewScreen

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.ExpandLess
import androidx.compose.material.icons.filled.ExpandMore
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import pangolin.backpackingbuddy.data.dataEntries.Campsite
import pangolin.backpackingbuddy.data.dataEntries.Trail
import pangolin.backpackingbuddy.ui.navigation.specs.ProfileScreenSpec
import pangolin.backpackingbuddy.ui.sharedComponents.BulletPoint
import pangolin.backpackingbuddy.ui.sharedComponents.ExpandableIcon

@Composable
fun OverviewDropdown(
    title: String,
    expanded: Boolean,
    onToggle: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onToggle() }
            .padding(vertical = 8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = title,
            style = MaterialTheme.typography.titleMedium,
            modifier = Modifier.weight(1f)
        )
        Icon(
            imageVector = if (expanded) Icons.Default.ExpandLess else Icons.Default.ExpandMore,
            contentDescription = null
        )
    }
}

@Composable
fun CampsiteOverviewItem(
    campsite: Campsite,
    onCampsiteClick: (Campsite) -> Unit
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 6.dp)
                .clip(RoundedCornerShape(10.dp))
                .background(MaterialTheme.colorScheme.secondaryContainer)
                .padding(12.dp)
                .clickable {
                    onCampsiteClick(campsite)
                }
        ) {
            Text(
                text = campsite.name,
                style = MaterialTheme.typography.titleMedium,
                color = MaterialTheme.colorScheme.onSecondaryContainer
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = "Location: Lat: %.2f, Lon: %.2f".format(campsite.lat, campsite.lon),
                style = MaterialTheme.typography.bodySmall
            )
        }
}

@Composable
fun TrailOverviewItem(
    trail: Trail,
    onTrailClick: (Trail) -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 6.dp)
            .clip(RoundedCornerShape(10.dp))
            .background(MaterialTheme.colorScheme.secondaryContainer)
            .padding(12.dp)
            .clickable {
                onTrailClick(trail)
            }
    ) {
        Text(
            text = trail.name,
            style = MaterialTheme.typography.titleMedium,
            color = MaterialTheme.colorScheme.onSecondaryContainer
        )
        Spacer(modifier = Modifier.height(4.dp))
        Text(
            text = "Distance: ${trail.distance} km",
            style = MaterialTheme.typography.bodySmall
        )
        Text(
            text = "Location: ${trail.location}",
            style = MaterialTheme.typography.bodySmall
        )
    }
}

@Composable
fun OverviewForEach(text : String) {
    Row (modifier = Modifier
        .fillMaxWidth()
        .padding(start=16.dp)
        .padding(top=3.dp)) {

        BulletPoint()
        Text (
            text = text
        )
    }
}