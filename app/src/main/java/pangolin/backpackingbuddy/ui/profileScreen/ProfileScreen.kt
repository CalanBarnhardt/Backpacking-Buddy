package pangolin.backpackingbuddy.ui.profileScreen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Button
import androidx.compose.material3.Divider
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
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
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp)
            .background(MaterialTheme.colorScheme.background),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = Icons.Filled.Person,
                contentDescription = "Person Icon",
                modifier = Modifier.size(48.dp)
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text(
                "Calan",
                fontSize = 30.sp,
                fontWeight = FontWeight.Bold
            )
            Spacer(modifier = Modifier.weight(1f))

            OutlinedButton(onClick = { onSignout() }) {
                Text(stringResource(R.string.signout))
            }
        }

        Spacer(modifier = Modifier.height(32.dp))

        Button(
            onClick = { onCreateTrip() },
            modifier = Modifier.fillMaxWidth(0.8f),
            shape = MaterialTheme.shapes.medium,
            contentPadding = PaddingValues(16.dp)
        ) {
            Text(stringResource(R.string.new_trip))
        }

        Spacer(modifier = Modifier.height(32.dp))

        Text(
            stringResource(R.string.created_trips),
            fontSize = 28.sp,
            fontWeight = FontWeight.SemiBold
        )

        Spacer(modifier = Modifier.height(8.dp))

        Divider(modifier = Modifier.fillMaxWidth(0.7f))

        Spacer(modifier = Modifier.height(16.dp))

        LazyColumn(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally,
            contentPadding = PaddingValues(vertical = 8.dp)
        ) {
            items(TripsRepo.trip) { trip ->
                Button(
                    onClick = { onExistingTrip(trip) },
                    modifier = Modifier.fillMaxWidth(0.8f),
                    shape = MaterialTheme.shapes.medium,
                    contentPadding = PaddingValues(24.dp)
                ) {
                    Text(trip.tripNameId)
                }
                Spacer(modifier = Modifier.height(16.dp))
            }
        }
    }
}

@Preview
@Composable
fun PreviewProfileScreen() {
    ProfileScreen({}, {}, {})
}