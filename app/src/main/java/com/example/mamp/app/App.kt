package com.example.mamp.app

import android.app.Application
import com.example.mamp.di.components.AppComponent
import com.example.mamp.di.components.DaggerAppComponent

class App : Application() {

    override fun onCreate() {
        super.onCreate()
        appComponent = DaggerAppComponent.factory().create(this)
    }

    companion object {
        lateinit var appComponent: AppComponent
            private set
    }
}