package pangolin.backpackingbuddy.ui.sharedComponents

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.sp

@Composable
fun TripNameDisplay (name : String) {
    Text (
        text = name,
        fontSize = (24.sp),
        textAlign = TextAlign.Center,
        modifier = Modifier
            .drawBehind {
                val strokeWidth = 7f
                val y = size.height - strokeWidth
                drawLine(
                    color = Color.Black,
                    start = Offset(0f, y),
                    end = Offset(size.width, y),
                    strokeWidth = strokeWidth
                )
            }
    )
}