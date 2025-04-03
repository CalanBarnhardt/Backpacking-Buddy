package pangolin.backpackingbuddy.ui.signupScreen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import pangolin.backpackingbuddy.R

@Composable
fun SignupScreen(
    emailValue: String,
    passwordValue: String,
    isLoading: Boolean,
    errorMessage: String?,
    onEmailChange: (String) -> Unit,
    onPasswordChange: (String) -> Unit,
    onSignup: () -> Unit,
    onLogin: () -> Unit,
) {
    var passwordVisible by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp)
            .background(MaterialTheme.colorScheme.background),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = stringResource(R.string.signup),
            fontSize = 36.sp,
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Center,
            modifier = Modifier.padding(bottom = 32.dp)
        )

        OutlinedTextField(
            value = emailValue,
            onValueChange = onEmailChange,
            modifier = Modifier.fillMaxWidth(),
            label = { Text(stringResource(R.string.email_address)) },
            placeholder = { Text(stringResource(R.string.type_email)) },
            leadingIcon = { Icon(Icons.Filled.Email, contentDescription = "Email Icon") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
            singleLine = true,
            isError = errorMessage != null
        )

        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(
            value = passwordValue,
            onValueChange = onPasswordChange,
            modifier = Modifier.fillMaxWidth(),
            label = { Text(stringResource(R.string.password)) },
            placeholder = { Text(stringResource(R.string.type_password)) },
            leadingIcon = { Icon(Icons.Filled.Lock, contentDescription = "Password Icon") },
            visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
            singleLine = true,
            trailingIcon = {
                val image = if (passwordVisible)
                    Icons.Filled.Visibility
                else Icons.Filled.VisibilityOff
                val description = if (passwordVisible) "Hide password" else "Show password"
                IconButton(onClick = { passwordVisible = !passwordVisible }) {
                    Icon(imageVector = image, contentDescription = description)
                }
            },
            isError = errorMessage != null
        )

        errorMessage?.let {
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = it,
                color = MaterialTheme.colorScheme.error,
                style = MaterialTheme.typography.bodySmall,
                textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxWidth()
            )
        }

        Spacer(modifier = Modifier.height(32.dp))

        Button(
            onClick = onSignup,
            modifier = Modifier.fillMaxWidth(0.8f),
            shape = MaterialTheme.shapes.medium,
            contentPadding = PaddingValues(16.dp),
            enabled = !isLoading
        ) {
            if (isLoading) {
                CircularProgressIndicator(
                    modifier = Modifier.size(24.dp),
                    color = MaterialTheme.colorScheme.onPrimary,
                    strokeWidth = 2.dp
                )
            } else {
                Text(stringResource(R.string.signup))
            }
        }

        Spacer(modifier = Modifier.height(48.dp))

        Box(
            modifier = Modifier.fillMaxWidth(),
            contentAlignment = Alignment.Center
        ) {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Text(
                    stringResource(R.string.have_account),
                    textAlign = TextAlign.Center,
                    modifier = Modifier.padding(bottom = 8.dp)
                )
                OutlinedButton(
                    onClick = onLogin,
                    modifier = Modifier.fillMaxWidth(0.8f),
                    shape = MaterialTheme.shapes.medium,
                    contentPadding = PaddingValues(16.dp),
                    enabled = !isLoading
                ) {
                    Text(stringResource(R.string.login))
                }
            }
        }
    }
}

@Preview(showBackground = true, device = "spec:width=411dp,height=891dp")
@Composable
fun PreviewSignupScreen() {
    SignupScreen(
        emailValue = "test@example.com",
        passwordValue = "password",
        isLoading = false,
        errorMessage = null,
        onEmailChange = {},
        onPasswordChange = {},
        onSignup = {},
        onLogin = {}
    )
}

@Preview(showBackground = true, device = "spec:width=411dp,height=891dp")
@Composable
fun PreviewSignupScreenLoading() {
    SignupScreen(
        emailValue = "test@example.com",
        passwordValue = "password",
        isLoading = true,
        errorMessage = null,
        onEmailChange = {},
        onPasswordChange = {},
        onSignup = {},
        onLogin = {}
    )
}

