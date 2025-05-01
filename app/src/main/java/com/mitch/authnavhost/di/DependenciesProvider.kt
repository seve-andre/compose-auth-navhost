package com.mitch.authnavhost.di

import com.mitch.authnavhost.data.auth.AuthRepository

interface DependenciesProvider {
    val authRepository: AuthRepository
}
