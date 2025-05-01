package com.mitch.authnavhost.ui.screens.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mitch.authnavhost.data.auth.AuthRepository
import com.mitch.authnavhost.util.result.Result
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch

class LoginViewModel(
    private val authRepository: AuthRepository
) : ViewModel() {
    private val eventChannel = Channel<LoginEvent>()
    val events = eventChannel.receiveAsFlow()

    fun onLogin() {
        viewModelScope.launch {
            val loginResult = authRepository.login()
            when (loginResult) {
                is Result.Success<*> -> eventChannel.send(LoginEvent.LoginSuccess)
                is Result.Error<*> -> eventChannel.send(LoginEvent.Error)
            }
        }
    }
}

sealed interface LoginEvent {
    data object Error : LoginEvent
    data object LoginSuccess : LoginEvent
}
