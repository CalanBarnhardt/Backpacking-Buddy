package pangolin.backpackingbuddy.ui.sharedComponents

import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import pangolin.backpackingbuddy.R

@Composable
fun NavButton(buttonText : String ) {
    Button(onClick = {}) {
        Text(buttonText)
    }
}