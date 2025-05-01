package pangolin.backpackingbuddy.ui.sharedComponents

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp

@Composable
fun IconHeaderButton(headerText: Int, buttonText: Int, icon: ImageVector) {
    var text by remember { mutableStateOf("") }

    Column {
        Text(text = stringResource(headerText))
        TextField(
            value = text,
            onValueChange = { text = it },
            label = { Text(stringResource(buttonText)) },
            leadingIcon = {
                Icon(
                    imageVector = icon,
                    contentDescription = "Icon",
                    modifier = Modifier.size(24.dp)
                )
            }
        )
    }
}
