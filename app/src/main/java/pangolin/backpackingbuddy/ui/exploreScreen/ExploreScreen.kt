package pangolin.backpackingbuddy.ui.exploreScreen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import pangolin.backpackingbuddy.ui.sharedComponents.SearchBar

@Composable
fun ExploreScreen() {
    Column(
        modifier = Modifier.fillMaxHeight()
    ) {
        Row(
            modifier = Modifier.fillMaxWidth()
        ) {
            SearchBar() // search bar will hold trails and campsites
        }

        Row(
            modifier = Modifier.fillMaxWidth()
        )  {
            Box(
                modifier = Modifier
                    .padding(top = 0.dp, bottom = 10.dp, start = 10.dp, end = 10.dp)
                    .fillMaxSize()
                    .background(color = Color.Gray),
                contentAlignment = Alignment.Center,
            ) {
                // holds the map -> will replace text with the actual map eventually
                // will need to add in when a trail/campsite is selected -> highlight it in map
                Text(text = "map")
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun PreviewExploreScreen() {
    ExploreScreen()
}