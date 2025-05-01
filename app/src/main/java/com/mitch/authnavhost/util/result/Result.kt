package com.mitch.authnavhost.util.result

// add compilerOptions -> freeCompilerArgs opt in for ExperimentalContracts
sealed class Result<out S, out E> {
    data class Success<out S>(val value: S) : Result<S, Nothing>()
    data class Error<out E>(val value: E) : Result<Nothing, E>()
}
