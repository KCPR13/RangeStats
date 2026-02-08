package pl.kacper.misterski.rangestats.ui.login

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LoginScreen(
    onDismiss: () -> Unit,
    model: LoginUiModel,
    onAction: (LoginAction) -> Unit,
) {
    ModalBottomSheet(onDismiss
    ) {
        LoginContent(model,onAction)
    }
}

@Composable
private fun LoginContent(
    model: LoginUiModel,
    onAction: (LoginAction) -> Unit,
){

    Column(
        modifier = Modifier
            .wrapContentSize()
            .padding(horizontal = 16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = "Log in",
            style = MaterialTheme.typography.headlineMedium
        )

        Spacer(Modifier.height(32.dp))

        OutlinedTextField(
            value = model.email,
            onValueChange = { newMail ->
                onAction(LoginAction.EmailChanged(newMail))
            },
            label = { Text("Email") },
            modifier = Modifier.fillMaxWidth(),
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Email
            ),
            singleLine = true,
        )

        Spacer(Modifier.height(16.dp))

        OutlinedTextField(
            value = model.password,
            onValueChange = { newPassword ->
                onAction(LoginAction.PasswordChanged(newPassword))
            },
            label = { Text("Password") },
            modifier = Modifier.fillMaxWidth(),
            visualTransformation = PasswordVisualTransformation(),
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Password
            ),
            singleLine = true
        )

        Spacer(Modifier.height(32.dp))
    }
}

@Composable
@Preview(showBackground = true)
private fun LoginScreenPreview(){
    MaterialTheme {
        LoginScreen(
            onDismiss = {},
            model = LoginUiModel("test@sample.com"),
            onAction = {}
        )
    }
}