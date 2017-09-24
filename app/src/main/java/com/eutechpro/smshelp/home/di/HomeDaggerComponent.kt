package com.eutechpro.smshelp.home.di

import com.eutechpro.smshelp.SmsHelpApplication
import com.eutechpro.smshelp.di.ActivityScope
import com.eutechpro.smshelp.home.HomeActivity
import com.eutechpro.smshelp.home.Mvp
import dagger.Component

@ActivityScope
@Component(modules = arrayOf(
        SmsHelpApplication.ApplicationDaggerModule::class,
        HomeDaggerModule::class
))
interface HomeDaggerComponent {
    fun inject(homeActivity: HomeActivity)
    fun getPresenter(): Mvp.Presenter
}
