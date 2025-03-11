package pangolin.backpackingbuddy.ui.sharedComponents

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

@Composable
fun AddButtonIcon(onAddButtonClick: () -> Unit, color: Color) {
    IconButton(onClick = onAddButtonClick) {
        Icon(
            imageVector = Icons.Default.AddCircle,
            contentDescription = "Add",
            tint = color
        )
    }
}