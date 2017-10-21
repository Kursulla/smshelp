package com.eutechpro.smshelp.home.di

import android.content.Context
import com.eutechpro.smshelp.di.ActivityScope
import com.eutechpro.smshelp.extensions.sharedPreferences
import com.eutechpro.smshelp.home.Model
import com.eutechpro.smshelp.home.Mvp
import com.eutechpro.smshelp.home.Presenter
import com.eutechpro.smshelp.scheduler.SmsAlarmScheduler
import com.eutechpro.smshelp.sms.persistance.SmsPrefsRepository
import dagger.Module
import dagger.Provides

@Module
internal class HomeDaggerModule {
    @ActivityScope
    @Provides
    fun providesPresenter(applicationContext: Context): Mvp.Presenter {
        val alarmScheduler = SmsAlarmScheduler(applicationContext)
        val repository = SmsPrefsRepository(applicationContext.sharedPreferences())
        val model = Model(alarmScheduler, repository)
        return Presenter(model)
    }
}
