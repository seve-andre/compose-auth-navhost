package com.mitch.authnavhost.data.auth

import com.mitch.authnavhost.util.result.Result
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.map

class AuthRepository(private val authDataSource: AuthDataSource) {
    val isLoggedIn = authDataSource.data.map { it.isLoggedIn }

    suspend fun login(): Result<Unit, Unit> {
        delay(300)
        authDataSource.login()
        return Result.Success(Unit)
    }

    suspend fun logout() {
        authDataSource.logout()
    }
}
