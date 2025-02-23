package pangolin.backpackingbuddy.ui.sharedComponents

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable

@Composable
fun AddButtonIcon() {
    Icon (
        imageVector = Icons.Default.AddCircle,
        contentDescription = "Add"
    )
}