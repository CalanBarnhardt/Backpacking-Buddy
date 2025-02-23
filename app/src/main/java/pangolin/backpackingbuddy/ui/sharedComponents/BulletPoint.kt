package pangolin.backpackingbuddy.ui.sharedComponents

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable

@Composable
fun BulletPoint () {
    Icon (
        imageVector = Icons.Filled.PlayArrow,
        contentDescription = "Bullet Point"
    )
}