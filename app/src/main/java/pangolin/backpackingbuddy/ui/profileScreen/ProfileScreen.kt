package pangolin.backpackingbuddy.ui.profileScreen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Button
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import org.intellij.lang.annotations.JdkConstants.HorizontalAlignment
import pangolin.backpackingbuddy.R
import pangolin.backpackingbuddy.data.Trip
import pangolin.backpackingbuddy.data.TripsRepo

@Composable
fun ProfileScreen(onCreateTrip: () -> Unit, onExistingTrip: (Trip) -> Unit, onSignout: () -> Unit) {
    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Spacer(modifier = Modifier.weight(0.05f))
            Icon(
                imageVector = Icons.Filled.Person,
                contentDescription = "Person Icon",
                modifier = Modifier.size(40.dp)
            )
            Text(
                "Calan",
                fontSize = 25.sp
            )
            Spacer(modifier = Modifier.weight(0.9f))

            // signout button
            Button(onClick = { onSignout() }) {
                Text(stringResource(R.string.signout))
            }
            Spacer(modifier = Modifier.weight(0.05f))
        }

        Spacer(modifier = Modifier.size(40.dp))

        // create a new trip button
        Button(onClick = { onCreateTrip() }) {
            Text(stringResource(R.string.new_trip))
        }
        Spacer(modifier = Modifier.size(10.dp))
        Text(
            stringResource(R.string.created_trips),
            fontSize = 25.sp
        )
        Spacer(modifier = Modifier.size(5.dp))
        HorizontalDivider(
            modifier = Modifier.fillMaxWidth(0.7f)
        )
        Spacer(modifier = Modifier.size(15.dp))
        LazyColumn(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            items(TripsRepo.trip) { trip ->
                Spacer(modifier = Modifier.size(15.dp))
                Button(onClick = { onExistingTrip(trip) }) {
                    Text(trip.tripNameId)
                }
            }
        }

    }
}

@Preview
@Composable
fun PreviewProfileScreen() {
    ProfileScreen({}, {}, {})
}