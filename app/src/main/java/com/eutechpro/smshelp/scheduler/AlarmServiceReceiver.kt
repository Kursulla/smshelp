package com.eutechpro.smshelp.scheduler

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log

class AlarmServiceReceiver : BroadcastReceiver() {
    private val TAG: String = "AlarmServiceReceiver"

    override fun onReceive(context: Context?, intent: Intent?) {
        Log.d(TAG, "Send.....")

    }


}