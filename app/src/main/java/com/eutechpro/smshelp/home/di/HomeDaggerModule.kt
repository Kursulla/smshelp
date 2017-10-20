package com.eutechpro.smshelp.home.di

import android.content.Context
import com.eutechpro.smshelp.alarm.PersistableSmsAlarmScheduler
import com.eutechpro.smshelp.di.ActivityScope
import com.eutechpro.smshelp.home.Model
import com.eutechpro.smshelp.home.Mvp
import com.eutechpro.smshelp.home.Presenter
import dagger.Module
import dagger.Provides

@Module
internal class HomeDaggerModule {
    @ActivityScope
    @Provides
    fun providesPresenter(applicationContext: Context): Mvp.Presenter {
        val alarmScheduler = PersistableSmsAlarmScheduler(applicationContext)
        val model = Model(alarmScheduler)
        return Presenter(model)
    }
}
