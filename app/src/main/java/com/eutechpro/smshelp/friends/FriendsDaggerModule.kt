package com.eutechpro.smshelp.friends

import android.content.res.AssetManager
import com.eutechpro.smshelp.di.ActivityScope
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