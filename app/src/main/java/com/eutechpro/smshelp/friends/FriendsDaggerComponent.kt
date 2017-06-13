package com.eutechpro.smshelp.friends

import com.eutechpro.smshelp.SmsHelpApplication

import dagger.Component
import javax.inject.Singleton


@Singleton
@Component(modules = arrayOf(
        SmsHelpApplication.ApplicationDaggerModule::class,
        FriendsDaggerModule::class
))
interface FriendsDaggerComponent {
    fun inject(activity: FoundationFriendsActivity)
}
