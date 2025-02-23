package pangolin.backpackingbuddy.ui.sharedComponents

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import pangolin.backpackingbuddy.R

@Composable
fun IconHeaderButton(headerText: Int, buttonText: Int, icon: ImageVector) {
    Column {
        Text(text = stringResource(headerText))
        Button(onClick = {}) {
            Row {
                Icon(
                    imageVector = icon,
                    contentDescription = "Icon",
                    modifier = Modifier.size(24.dp)
                )
                Spacer(modifier = Modifier.size(8.dp))
                Text(stringResource(buttonText))
            }
        }
    }
}
