package com.mitch.authnavhost.ui.screens.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mitch.authnavhost.data.auth.AuthRepository
import kotlinx.coroutines.launch

class HomeViewModel(
    private val authRepository: AuthRepository
) : ViewModel() {

    fun onLogout() {
        viewModelScope.launch {
            authRepository.logout()
        }
    }
}
