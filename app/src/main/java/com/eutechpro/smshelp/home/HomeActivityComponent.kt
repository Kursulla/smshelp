package com.eutechpro.smshelp.home

import com.eutechpro.smshelp.SmsHelpApplication
import dagger.Component
import home.HomeActivity
import javax.inject.Singleton

@Singleton
@Component(modules = arrayOf(
        SmsHelpApplication.ApplicationDaggerModule::class,
        HomeDaggerModule::class
))
interface HomeActivityComponent {
    fun inject(homeActivity: HomeActivity)
}
