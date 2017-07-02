package com.eutechpro.smshelp.home

import android.content.Context
import com.eutechpro.smshelp.alarm.PersistableSmsScheduler
import com.eutechpro.smshelp.di.ActivityScope
import dagger.Module
import dagger.Provides

@Module
internal class HomeDaggerModule {
    @ActivityScope
    @Provides
    fun providesPresenter(applicationContext: Context): Mvp.Presenter {
        val alarmScheduler = PersistableSmsScheduler(applicationContext)
        val model = Model(alarmScheduler)
        return Presenter(model)
    }
}
