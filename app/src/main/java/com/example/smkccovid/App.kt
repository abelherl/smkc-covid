package com.example.smkccovid

import android.app.Application
import android.content.res.Resources
import com.google.firebase.FirebaseApp

class App : Application() {
    override fun onCreate() {
        super.onCreate()
        instance = this
        resourses = this.resources
        FirebaseApp.initializeApp(this)
    }

    companion object {
        var instance: App? = null
            private set
        var resourses: Resources? = null
            private set
    }
}