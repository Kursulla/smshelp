package com.eutechpro.smshelp

import android.app.Application
import com.facebook.stetho.Stetho

class SmsHelpApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        Stetho.initializeWithDefaults(this)
    }
}