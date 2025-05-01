package com.mitch.authnavhost.di

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import com.mitch.authnavhost.data.auth.AuthDataSource
import com.mitch.authnavhost.data.auth.AuthRepository

class DefaultDependenciesProvider(
    private val context: Context
) : DependenciesProvider {

    private val Context.userPrefsDatastore: DataStore<Preferences> by preferencesDataStore(name = "user_preferences")

    private val authDataSource by lazy {
        AuthDataSource(context.userPrefsDatastore)
    }
    override val authRepository: AuthRepository by lazy {
        AuthRepository(authDataSource = authDataSource)
    }
}
