package pangolin.backpackingbuddy.ui.sharedComponents

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import pangolin.backpackingbuddy.ui.theme.DarkForestGreen

@Composable
fun TripNameDisplay(name: String) {
    Text(
        text = name,
        fontSize = 30.sp,
        fontWeight = FontWeight.Bold,
        color = DarkForestGreen,
        textAlign = TextAlign.Center,
        modifier = Modifier
            .padding(8.dp)
            .drawBehind {
                val strokeWidth = 3f
                val y = size.height - strokeWidth
                drawLine(
                    color = Color.Gray,
                    start = Offset(0f, y),
                    end = Offset(size.width, y),
                    strokeWidth = strokeWidth
                )
            }
    )
}

@Preview
@Composable
fun PreviewTripNameDisplay() {
    TripNameDisplay("Durango")
}