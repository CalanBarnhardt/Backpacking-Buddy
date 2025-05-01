package pangolin.backpackingbuddy.ui.profileScreen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
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
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.flow.Flow
import org.intellij.lang.annotations.JdkConstants.HorizontalAlignment
import pangolin.backpackingbuddy.R
import pangolin.backpackingbuddy.viewmodel.BackpackingBuddyViewModel

@Composable
fun ProfileScreen(viewModel: BackpackingBuddyViewModel, onCreateTrip: () -> Unit, onExistingTrip: (String) -> Unit, onSignout: () -> Unit,
                  ) {
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
            BoxWithConstraints(
                modifier = Modifier.weight(1f) // Take available space, leaving room for the button
            ) {
                val fontSize = when {
                    maxWidth < 150.dp -> 16.sp // Smaller font for tight space
                    maxWidth < 200.dp -> 18.sp // Medium font for moderate space
                    else -> 20.sp // Default font size
                }
                Text(
                    text = viewModel.getCurrentEmail(),
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis, // Show ellipsis if text is too long
                    modifier = Modifier.align(Alignment.CenterStart)
                )
            }
            Spacer(modifier = Modifier.weight(.1f))

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

        // displays a button for each existing trip

        // retrieves list of trip names
        val tripList by viewModel.retrieveNames().collectAsState(initial = emptyList())

        LazyColumn(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally,
            contentPadding = PaddingValues(vertical = 8.dp)
        ) {

            items(tripList) { trip ->
                Button(
                    onClick = {
                        if (trip != null) {
                            onExistingTrip(trip)
                        }
                    },
                    modifier = Modifier.fillMaxWidth(0.8f),
                    shape = MaterialTheme.shapes.medium,
                    contentPadding = PaddingValues(24.dp)
                ) {
                    if (trip != null) {
                        Text(trip)
                    }
                }
                Spacer(modifier = Modifier.height(16.dp))
            }
        }
    }
}

@Preview
@Composable
fun PreviewProfileScreen() {
    //ProfileScreen({}, {}, {})
}