package pangolin.backpackingbuddy.ui.sharedComponents

import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import pangolin.backpackingbuddy.R
import pangolin.backpackingbuddy.ui.theme.BackpackingBuddyTheme

@Composable
fun NavButton(buttonText : String ) {
    Button(onClick = {}, colors = androidx.compose.material3.ButtonDefaults.buttonColors(
        containerColor = MaterialTheme.colorScheme.primary,
        contentColor = MaterialTheme.colorScheme.onPrimary
    )
    ) {
        Text(buttonText)
    }
}

@Preview
@Composable
fun PreviewNavButton() {
    BackpackingBuddyTheme{
    NavButton("hello")
    }
}