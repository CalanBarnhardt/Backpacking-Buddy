package pangolin.backpackingbuddy.ui.tripOverviewScreen

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import pangolin.backpackingbuddy.ui.sharedComponents.BulletPoint
import pangolin.backpackingbuddy.ui.sharedComponents.ExpandableIcon

@Composable
fun OverviewDropdown(isExpanded: Boolean, text: String) : Boolean {
    var expandedToggle = isExpanded
    Row(modifier = Modifier
        .fillMaxWidth()) {
        ExpandableIcon(16.dp, expandedToggle) {
            expandedToggle = !expandedToggle
        }
        Text (
            text = text,
            modifier = Modifier.padding(start = 16.dp)
        )
    }

    return expandedToggle
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