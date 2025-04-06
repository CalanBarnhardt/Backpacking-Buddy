package pangolin.backpackingbuddy.ui.createTrip

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
@Composable
fun CreateNewTripName(onClick : (String) -> Unit) {
    val inputText = remember { androidx.compose.runtime.mutableStateOf(TextFieldValue()) }
    val keyboardController = LocalSoftwareKeyboardController.current

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.fillMaxWidth()
    ) {
        Spacer(modifier = Modifier.size(55.dp))
        Text(text = "Name this trip", style = MaterialTheme.typography.titleLarge)

        // display keyboard
        BasicTextField(
            value = inputText.value,
            onValueChange = { inputText.value = it },
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
                .height(50.dp)
                .background(Color.Gray)
                .padding(16.dp),
            textStyle = TextStyle(color = Color.White),
            keyboardOptions = KeyboardOptions.Default.copy(
                keyboardType = KeyboardType.Text
            ),
            keyboardActions = KeyboardActions(
                onDone = { keyboardController?.hide() }
            )
        )

        Button(
            onClick = { onClick(inputText.value.text) },
            modifier = Modifier.padding(top = 16.dp)
        ) {
            Text(text = "Next")
        }
    }
}

@Preview
@Composable
fun PreviewCreateNewTripName() {
    CreateNewTripName({})
}