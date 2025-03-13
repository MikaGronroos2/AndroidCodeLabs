package com.example.simplenotes2

import android.app.Application
import android.content.Context

class EduskuntaApplication: Application() {

    companion object {
        lateinit var appContext: Context
    }

    override fun onCreate() {
        super.onCreate()
        appContext = applicationContext
    }

}