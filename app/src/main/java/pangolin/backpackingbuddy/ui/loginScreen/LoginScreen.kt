package pangolin.backpackingbuddy.ui.loginScreen

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import pangolin.backpackingbuddy.R
import pangolin.backpackingbuddy.ui.sharedComponents.IconHeaderButton

@Composable
fun LoginScreen(onLogin: () -> Unit, onSignup: () -> Unit){
    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = stringResource(R.string.login),
            fontSize = 28.sp
        )
        Spacer(modifier = Modifier.size(50.dp))
        IconHeaderButton(R.string.username, R.string.type_username, Icons.Filled.Person)
        Spacer(modifier = Modifier.size(10.dp))
        IconHeaderButton(R.string.password, R.string.type_password, Icons.Filled.Lock)

        Spacer(modifier = Modifier.size(20.dp))
        Button(onClick = { onLogin() }) {
            Text(stringResource(R.string.login))
        }

        Spacer(modifier = Modifier.size(100.dp))
        Box(
            modifier = Modifier.fillMaxWidth(),
            contentAlignment = Alignment.Center
        ) {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Text(
                    stringResource(R.string.no_account),
                    modifier = Modifier.width(80.dp)
                )
                Button(onClick = { onSignup() }) {
                    Text(stringResource(R.string.signup))
                }
            }
        }
    }
}

@Preview
@Composable
fun PreviewLoginScreen() {
    LoginScreen({}, {})
}