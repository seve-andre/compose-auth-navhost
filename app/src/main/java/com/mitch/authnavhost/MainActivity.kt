package com.mitch.authnavhost

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.SystemBarStyle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.WindowInsetsSides
import androidx.compose.foundation.layout.consumeWindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.only
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.DisposableEffect
import androidx.compose.ui.Modifier
import androidx.navigation.compose.rememberNavController
import com.mitch.authnavhost.ui.navigation.AppDestination
import com.mitch.authnavhost.ui.navigation.AppNavHost
import com.mitch.authnavhost.ui.theme.AuthNavHostTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        enableEdgeToEdge()
        super.onCreate(savedInstanceState)
        val dependenciesProvider = (application as AuthNavHostApplication).dependenciesProvider

        setContent {
            val navController = rememberNavController()
            val isThemeDark = isSystemInDarkTheme()

            DisposableEffect(isThemeDark) {
                enableEdgeToEdge(
                    statusBarStyle = SystemBarStyle.auto(
                        android.graphics.Color.TRANSPARENT,
                        android.graphics.Color.TRANSPARENT
                    ) { isThemeDark },
                    navigationBarStyle = SystemBarStyle.auto(
                        LightScrim,
                        DarkScrim
                    ) { isThemeDark }
                )

                onDispose { }
            }

            AuthNavHostTheme {
                Scaffold(contentWindowInsets = WindowInsets(0)) { padding ->
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(padding)
                            .consumeWindowInsets(padding)
                            .windowInsetsPadding(WindowInsets.safeDrawing.only(WindowInsetsSides.Horizontal))
                    ) {
                        AppNavHost(
                            dependenciesProvider = dependenciesProvider,
                            navController = navController,
                            startDestination = AppDestination.Screen.Home
                        )
                    }
                }
            }
        }
    }
}

/**
 * The default light scrim, as defined by androidx and the platform:
 * https://cs.android.com/androidx/platform/frameworks/support/+/androidx-main:activity/activity/src/main/java/androidx/activity/EdgeToEdge.kt;l=35-38;drc=27e7d52e8604a080133e8b842db10c89b4482598
 */
private val LightScrim = android.graphics.Color.argb(0xe6, 0xFF, 0xFF, 0xFF)

/**
 * The default dark scrim, as defined by androidx and the platform:
 * https://cs.android.com/androidx/platform/frameworks/support/+/androidx-main:activity/activity/src/main/java/androidx/activity/EdgeToEdge.kt;l=40-44;drc=27e7d52e8604a080133e8b842db10c89b4482598
 */
private val DarkScrim = android.graphics.Color.argb(0x80, 0x1b, 0x1b, 0x1b)
