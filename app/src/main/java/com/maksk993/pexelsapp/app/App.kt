package com.maksk993.pexelsapp.app

import android.app.Application
import com.maksk993.pexelsapp.di.AppComponent
import com.maksk993.pexelsapp.di.AppModule
import com.maksk993.pexelsapp.di.DaggerAppComponent

class App : Application() {
    lateinit var appComponent: AppComponent

    override fun onCreate() {
        super.onCreate()
        appComponent = DaggerAppComponent.builder().appModule(AppModule(this)).build()
    }
}