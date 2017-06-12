package com.eutechpro.smshelp.friends

import android.content.res.AssetManager
import dagger.Module
import dagger.Provides

@Module
class FriendsDaggerModule {
    @Provides
    fun providesMvpPresenter(assetsManager: AssetManager): Mvp.Presenter {
        return Presenter(Model(assetsManager))
    }
}