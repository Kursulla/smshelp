package com.eutechpro.smshelp.home

import com.eutechpro.smshelp.SmsHelpApplication
import com.eutechpro.smshelp.di.ActivityScope
import dagger.Component

@ActivityScope
@Component(modules = arrayOf(
        SmsHelpApplication.ApplicationDaggerModule::class,
        HomeDaggerModule::class
))
interface HomeActivityComponent {
    fun inject(homeActivity: HomeActivity)
}
