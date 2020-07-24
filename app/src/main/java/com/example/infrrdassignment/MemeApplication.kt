package com.example.infrrdassignment

import android.app.Application
import android.content.Context

/**
 * This class is the Main Application class
 */
class MemeApplication : Application() {
    companion object {
        lateinit var appContext: Context
    }

    override fun onCreate() {
        super.onCreate()
        appContext = applicationContext
    }
}