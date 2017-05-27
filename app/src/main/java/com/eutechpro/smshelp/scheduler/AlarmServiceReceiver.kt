package com.eutechpro.smshelp.scheduler

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.debug

class AlarmServiceReceiver : BroadcastReceiver(), AnkoLogger {
    override fun onReceive(context: Context?, intent: Intent?) {
        debug( "Send.....")

    }


}