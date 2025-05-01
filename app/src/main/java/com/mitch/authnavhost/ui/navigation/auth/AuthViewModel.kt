package com.mitch.authnavhost.ui.navigation.auth

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mitch.authnavhost.data.auth.AuthRepository
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn

class AuthViewModel(
    authRepository: AuthRepository
) : ViewModel() {

    val authenticationState = authRepository.isLoggedIn.map { isLoggedIn ->
        when {
            isLoggedIn -> AuthenticationState.LoggedIn
            else -> AuthenticationState.NotLoggedIn
        }
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.Lazily,
        initialValue = AuthenticationState.Loading
    )
}

sealed interface AuthenticationState {
    data object Loading : AuthenticationState
    data object NotLoggedIn : AuthenticationState
    data object LoggedIn : AuthenticationState
}
