package com.eutechpro.smshelp.sms

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log

class AlarmServiceReceiver : BroadcastReceiver() {
    private val TAG: String = "AlarmServiceReceiver"

    override fun onReceive(context: Context?, itnent: Intent?) {
        val smsControll: SmsControll = SmsControll()
//        smsControll.scheduleNextAlarm(applicationContext)
        Log.d(TAG, "Send.....")
    }


}