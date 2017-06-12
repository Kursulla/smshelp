package com.eutechpro.smshelp.home

import android.content.Context
import com.eutechpro.smshelp.alarm.AlarmScheduler
import com.eutechpro.smshelp.alarm.persistance.PrefsAlarmRepository
import com.eutechpro.smshelp.extensions.sharedPreferences
import dagger.Module
import dagger.Provides

@Module
class HomeDaggerModule {
    @Provides
    fun providesPresenter(applicationContext: Context): Mvp.Presenter {
        val alarmScheduler = AlarmScheduler(applicationContext)
        val model = Model(PrefsAlarmRepository(applicationContext.sharedPreferences("sms_help_persistance")), alarmScheduler)
        return Presenter(model)
    }
}
