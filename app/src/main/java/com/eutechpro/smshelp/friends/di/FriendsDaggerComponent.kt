package com.eutechpro.smshelp.friends.di

import com.eutechpro.smshelp.SmsHelpApplication
import com.eutechpro.smshelp.di.ActivityScope
import com.eutechpro.smshelp.friends.FriendsActivity
import dagger.Component


@ActivityScope
@Component(modules = arrayOf(
        SmsHelpApplication.ApplicationDaggerModule::class,
        FriendsDaggerModule::class
))
interface FriendsDaggerComponent {
    fun inject(activity: FriendsActivity)
}
