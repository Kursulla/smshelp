package com.eutechpro.smshelp

import android.app.Application
import com.eutechpro.smshelp.friends.di.DaggerFriendsDaggerComponent
import com.eutechpro.smshelp.friends.di.FriendsDaggerComponent
import com.eutechpro.smshelp.friends.di.FriendsDaggerModule
import com.eutechpro.smshelp.home.di.DaggerHomeDaggerComponent
import com.eutechpro.smshelp.home.di.HomeDaggerComponent
import com.eutechpro.smshelp.home.di.HomeDaggerModule
import com.facebook.stetho.Stetho
import dagger.Module
import dagger.Provides

open class SmsHelpApplication : Application() {
    companion object {
        lateinit var homeDaggerComponent: HomeDaggerComponent
        lateinit var friendsDaggerComponent: FriendsDaggerComponent
    }
    override fun onCreate() {
        super.onCreate()
        if (needStetho()) {
            Stetho.initializeWithDefaults(this)
        }

        val applicationModule = ApplicationDaggerModule(this)

        homeDaggerComponent = DaggerHomeDaggerComponent.builder()
                .applicationDaggerModule(applicationModule)
                .homeDaggerModule(HomeDaggerModule())
                .build()

        friendsDaggerComponent = DaggerFriendsDaggerComponent.builder()
                .applicationDaggerModule(applicationModule)
                .friendsDaggerModule(FriendsDaggerModule())
                .build()
    }

    protected open fun needStetho(): Boolean {
        return BuildConfig.DEBUG
    }

    @Module
    class ApplicationDaggerModule(private var application: Application) {
        @Provides
        fun providesApplicationContext() = application.applicationContext!!

        @Provides
        fun providesAssetManager() = application.assets!!
    }
}