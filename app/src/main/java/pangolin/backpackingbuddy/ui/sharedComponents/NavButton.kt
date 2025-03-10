package pangolin.backpackingbuddy.ui.sharedComponents

import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.tooling.preview.Preview
import pangolin.backpackingbuddy.data.Trip
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.unit.dp

fun Modifier.conditional(condition : Boolean, modifier : Modifier.() -> Modifier) : Modifier {
    return if (condition) {
        (modifier(this))
    } else {
        this
    }
}


@Composable
fun NavButton(buttonText : String , trip: Trip, onClick: (Trip) -> Unit = {}, isPressed : Boolean = false) {
    Button(
        elevation = ButtonDefaults.elevatedButtonElevation(
            defaultElevation = if (!isPressed) 15.dp else 2.dp,
            pressedElevation = 15.dp,
            focusedElevation = 15.dp,
            hoveredElevation = 4.dp,
            disabledElevation = 2.dp
        ),
        onClick = { onClick(trip) },

        colors = ButtonDefaults.buttonColors(
            containerColor = when {
                isPressed -> MaterialTheme.colorScheme.primaryContainer
                else -> MaterialTheme.colorScheme.secondary
            },
            contentColor = when {
                isPressed -> MaterialTheme.colorScheme.tertiary
                else -> MaterialTheme.colorScheme.onPrimary
            }),
    ) {
        Text(buttonText)
    }
}

@Preview
@Composable
fun PreviewNavButton() {
    NavButton("hello", Trip("Durango", listOf("A", "B", "C"), listOf("A", "B", "C")))
}