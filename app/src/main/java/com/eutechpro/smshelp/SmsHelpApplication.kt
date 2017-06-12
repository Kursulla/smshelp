package com.eutechpro.smshelp

import android.app.Application
import android.content.Context
import android.content.res.AssetManager
import com.eutechpro.smshelp.friends.DaggerFriendsDaggerComponent
import com.eutechpro.smshelp.friends.FriendsDaggerComponent
import com.eutechpro.smshelp.friends.FriendsDaggerModule
import com.eutechpro.smshelp.home.DaggerHomeActivityComponent
import com.eutechpro.smshelp.home.HomeActivityComponent
import com.eutechpro.smshelp.home.HomeDaggerModule
import com.facebook.stetho.Stetho
import dagger.Module
import dagger.Provides

class SmsHelpApplication : Application() {
    companion object {
        @JvmStatic lateinit var homeActivityComponent: HomeActivityComponent
        @JvmStatic lateinit var friendsDaggerComponent: FriendsDaggerComponent
    }
    override fun onCreate() {
        super.onCreate()
        Stetho.initializeWithDefaults(this)
        val applicationModule = ApplicationDaggerModule(this)

        homeActivityComponent = DaggerHomeActivityComponent.builder()
                .applicationDaggerModule(applicationModule)
                .homeDaggerModule(HomeDaggerModule())
                .build()

        friendsDaggerComponent = DaggerFriendsDaggerComponent.builder()
                .applicationDaggerModule(applicationModule)
                .friendsDaggerModule(FriendsDaggerModule())
                .build()

    }

    @Module
    class ApplicationDaggerModule(private var application: Application) {
        @Provides
        fun providesApplicationContext(): Context {
            return application.applicationContext
        }

        @Provides
        fun providesAssetManager(): AssetManager {
            return application.assets
        }
    }
}