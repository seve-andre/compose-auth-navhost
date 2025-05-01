package com.mitch.authnavhost.ui.navigation

import androidx.compose.runtime.Composable
import androidx.lifecycle.compose.dropUnlessResumed
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navDeepLink
import com.mitch.authnavhost.di.DependenciesProvider
import com.mitch.authnavhost.ui.navigation.auth.authComposable
import com.mitch.authnavhost.ui.screens.home.HomeRoute
import com.mitch.authnavhost.ui.screens.home.HomeViewModel
import com.mitch.authnavhost.ui.screens.login.LoginRoute
import com.mitch.authnavhost.ui.screens.login.LoginViewModel
import com.mitch.authnavhost.ui.screens.profile.ProfileRoute
import com.mitch.authnavhost.util.viewModelProviderFactory

@Composable
fun AppNavHost(
    dependenciesProvider: DependenciesProvider,
    navController: NavHostController,
    startDestination: AppDestination
) {
    NavHost(
        navController = navController,
        startDestination = startDestination
    ) {
        composable<AppDestination.Screen.Login> {
            LoginRoute(
                onLoginSuccess = dropUnlessResumed {
                    navController.navigate(AppDestination.Screen.Home) {
                        popUpTo<AppDestination.Screen.Login> {
                            inclusive = true
                        }
                    }
                },
                viewModel = viewModel(
                    factory = viewModelProviderFactory {
                        LoginViewModel(authRepository = dependenciesProvider.authRepository)
                    }
                )
            )
        }

        authComposable<AppDestination.Screen.Home>(
            navController = navController,
            dependenciesProvider = dependenciesProvider,
        ) {
            HomeRoute(
                onNavigateToProfile = dropUnlessResumed {
                    navController.navigate(AppDestination.Screen.Profile)
                },
                viewModel(
                    factory = viewModelProviderFactory {
                        HomeViewModel(authRepository = dependenciesProvider.authRepository)
                    }
                )
            )
        }

        authComposable<AppDestination.Screen.Profile>(
            navController = navController,
            dependenciesProvider = dependenciesProvider,
            deepLinks = listOf(
                navDeepLink(
                    deepLinkBuilder = {
                        uriPattern = "https://example.com/profile"
                    }
                )
            )
        ) {
            ProfileRoute()
        }
    }
}
