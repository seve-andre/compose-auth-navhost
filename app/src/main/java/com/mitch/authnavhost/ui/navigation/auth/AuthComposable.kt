package com.mitch.authnavhost.ui.navigation.auth

import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.LocalActivity
import androidx.compose.animation.AnimatedContentScope
import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.animation.SizeTransform
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.getValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavDeepLink
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import com.mitch.authnavhost.di.DependenciesProvider
import com.mitch.authnavhost.ui.components.LoadingScreen
import com.mitch.authnavhost.ui.navigation.AppDestination
import com.mitch.authnavhost.util.viewModelProviderFactory
import kotlin.reflect.KType

inline fun <reified T : Any> NavGraphBuilder.authComposable(
    navController: NavHostController,
    dependenciesProvider: DependenciesProvider,
    typeMap: Map<KType, @JvmSuppressWildcards NavType<*>> = emptyMap(),
    deepLinks: List<NavDeepLink> = emptyList(),
    noinline enterTransition:
    (AnimatedContentTransitionScope<NavBackStackEntry>.() -> @JvmSuppressWildcards
    EnterTransition?)? =
        null,
    noinline exitTransition:
    (AnimatedContentTransitionScope<NavBackStackEntry>.() -> @JvmSuppressWildcards
    ExitTransition?)? =
        null,
    noinline popEnterTransition:
    (AnimatedContentTransitionScope<NavBackStackEntry>.() -> @JvmSuppressWildcards
    EnterTransition?)? =
        enterTransition,
    noinline popExitTransition:
    (AnimatedContentTransitionScope<NavBackStackEntry>.() -> @JvmSuppressWildcards
    ExitTransition?)? =
        exitTransition,
    noinline sizeTransform:
    (AnimatedContentTransitionScope<NavBackStackEntry>.() -> @JvmSuppressWildcards
    SizeTransform?)? =
        null,
    noinline content: @Composable AnimatedContentScope.(NavBackStackEntry) -> Unit
) {
    composable<T>(
        typeMap = typeMap,
        deepLinks = deepLinks,
        enterTransition = enterTransition,
        exitTransition = exitTransition,
        popEnterTransition = popEnterTransition,
        popExitTransition = popExitTransition,
        sizeTransform = sizeTransform,
    ) { navBackStackEntry ->
        val viewModel: AuthViewModel =
            viewModel(
                viewModelStoreOwner = LocalActivity.current as ComponentActivity,
                factory = viewModelProviderFactory {
                    AuthViewModel(authRepository = dependenciesProvider.authRepository)
                }
            )
        val authenticationState by viewModel.authenticationState.collectAsStateWithLifecycle(
            initialValue = AuthenticationState.Loading
        )
        SideEffect {
            Log.d("AuthComposable", "AuthenticationState: $authenticationState")
        }

        when (authenticationState) {
            AuthenticationState.Loading -> {
                LoadingScreen()
            }

            AuthenticationState.NotLoggedIn -> {
                LaunchedEffect(authenticationState) {
                    navController.navigate(AppDestination.Screen.Login) {
                        popUpTo(navController.graph.id) {
                            inclusive = true
                        }
                    }
                }
            }

            AuthenticationState.LoggedIn -> {
                content(navBackStackEntry)
            }
        }
    }
}
