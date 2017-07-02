package com.eutechpro.smshelp.friends

import com.eutechpro.smshelp.SmsHelpApplication
import com.eutechpro.smshelp.di.ActivityScope
import dagger.Component


@ActivityScope
@Component(modules = arrayOf(
        SmsHelpApplication.ApplicationDaggerModule::class,
        FriendsDaggerModule::class
))
interface FriendsDaggerComponent {
    fun inject(activity: FoundationFriendsActivity)
}
