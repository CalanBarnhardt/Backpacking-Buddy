package pangolin.backpackingbuddy.ui.sharedComponents

import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import pangolin.backpackingbuddy.R
import pangolin.backpackingbuddy.data.Trail
import pangolin.backpackingbuddy.data.Trip
import pangolin.backpackingbuddy.ui.theme.BackpackingBuddyTheme

@Composable
fun NavButton(buttonText : String , trip: Trip, onClick: (Trip) -> Unit = {}) {
    Button(onClick = { onClick(trip) }, colors = ButtonDefaults.buttonColors(
        containerColor = MaterialTheme.colorScheme.primaryContainer,
        contentColor = MaterialTheme.colorScheme.onPrimaryContainer
    )
    ) {
        Text(buttonText)
    }
}

@Preview
@Composable
fun PreviewNavButton() {
    NavButton("hello", Trip("Durango", listOf("A", "B", "C"), listOf("A", "B", "C")))
}