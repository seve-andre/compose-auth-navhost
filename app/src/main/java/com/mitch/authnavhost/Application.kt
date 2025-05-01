package com.mitch.authnavhost

import android.app.Application
import com.mitch.authnavhost.di.DefaultDependenciesProvider
import com.mitch.authnavhost.di.DependenciesProvider

class AuthNavHostApplication : Application() {

    lateinit var dependenciesProvider: DependenciesProvider

    override fun onCreate() {
        super.onCreate()
        dependenciesProvider = DefaultDependenciesProvider(this)
    }
}
