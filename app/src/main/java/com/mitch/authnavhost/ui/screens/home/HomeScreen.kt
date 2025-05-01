package com.mitch.authnavhost.ui.screens.home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import com.mitch.authnavhost.ui.theme.AuthNavHostTheme

@Composable
fun HomeRoute(onNavigateToProfile: () -> Unit, viewModel: HomeViewModel, modifier: Modifier = Modifier) {
    HomeScreen(
        onNavigateToProfile = onNavigateToProfile,
        onLogoutClick = viewModel::onLogout,
        modifier = modifier
    )
}

@Composable
private fun HomeScreen(
    onNavigateToProfile: () -> Unit,
    onLogoutClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .then(modifier),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = "Home Screen")
        Button(onClick = onNavigateToProfile) {
            Text(text = "Go to profile")
        }
        Button(
            onClick = onLogoutClick,
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFA42929))
        ) {
            Text(text = "Logout")
        }
    }
}

@Preview
@Composable
private fun HomeScreenPreview() {
    AuthNavHostTheme {
        HomeScreen(onNavigateToProfile = {}, onLogoutClick = {})
    }
}
