package com.mitch.authnavhost.ui.screens.login

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.mitch.authnavhost.ui.theme.AuthNavHostTheme
import com.mitch.authnavhost.util.ObserveAsEvents

@Composable
fun LoginRoute(
    onLoginSuccess: () -> Unit,
    viewModel: LoginViewModel,
    modifier: Modifier = Modifier,
) {
    ObserveAsEvents(viewModel.events) { event ->
        when (event) {
            LoginEvent.Error -> Unit
            LoginEvent.LoginSuccess -> onLoginSuccess()
        }
    }

    LoginScreen(
        modifier = modifier,
        onLoginClick = viewModel::onLogin
    )
}

@Composable
private fun LoginScreen(
    onLoginClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Scaffold(modifier = modifier.fillMaxSize()) { contentPadding ->
        Box(
            modifier = Modifier
                .padding(paddingValues = contentPadding)
                .fillMaxSize(),
            contentAlignment = Alignment.Center,
        ) {
            var showLoading by remember { mutableStateOf(false) }
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Spacer(Modifier.height(32.dp))
                if (showLoading) {
                    CircularProgressIndicator()
                } else {
                    Button(
                        onClick = {
                            onLoginClick()
                            showLoading = true
                        }
                    ) {
                        Text(
                            text = "Login",
                            style = MaterialTheme.typography.headlineSmall
                        )
                    }
                }
            }
        }
    }
}

@Preview
@Composable
private fun LoginScreenPreview() {
    AuthNavHostTheme {
        LoginScreen(onLoginClick = {})
    }
}
