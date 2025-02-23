package pangolin.backpackingbuddy.ui.sharedComponents

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material3.Icon

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.Dp

@Composable
fun ExpandableIcon(size: Dp, expanded : Boolean, onExpand : () -> Unit) {
    Icon(
        imageVector =
            if (expanded) Icons.Default.KeyboardArrowDown
            else Icons.Default.KeyboardArrowRight,
        contentDescription = "Expand",
        modifier = Modifier
            .clickable { onExpand () }
            .size(size)
    )
}