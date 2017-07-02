package com.eutechpro.smshelp.friends.di

import android.content.res.AssetManager
import com.eutechpro.smshelp.di.ActivityScope
import com.eutechpro.smshelp.friends.Model
import com.eutechpro.smshelp.friends.Mvp
import com.eutechpro.smshelp.friends.Presenter
import dagger.Module
import dagger.Provides

@Module
class FriendsDaggerModule {
    @Provides
    @ActivityScope
    fun providesMvpPresenter(assetsManager: AssetManager): Mvp.Presenter {
        return Presenter(Model(assetsManager))
    }
}