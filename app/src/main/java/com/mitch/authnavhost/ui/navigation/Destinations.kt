package com.mitch.authnavhost.ui.navigation

import kotlinx.serialization.Serializable

sealed interface AppDestination {

    sealed interface Screen : AppDestination {

        @Serializable
        data object Login : Screen

        @Serializable
        data object Home : Screen

        @Serializable
        data object Profile : Screen
    }
}
