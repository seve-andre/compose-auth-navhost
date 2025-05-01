package com.mitch.authnavhost.data.auth

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import kotlinx.coroutines.flow.map

class AuthDataSource(private val userPrefsDatastore: DataStore<Preferences>) {

    val data = userPrefsDatastore.data.map { prefs ->
        UserPreferences(isLoggedIn = prefs[isLoggedInKey] ?: false)
    }

    suspend fun login() {
        userPrefsDatastore.edit { prefs ->
            prefs[isLoggedInKey] = true
        }
    }

    suspend fun logout() {
        userPrefsDatastore.edit { prefs ->
            prefs[isLoggedInKey] = false
        }
    }
}

private val isLoggedInKey = booleanPreferencesKey("is_logged_in")

data class UserPreferences(val isLoggedIn: Boolean)
