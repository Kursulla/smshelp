package com.eutechpro.smshelp.scheduler

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import com.eutechpro.smshelp.sms.Sms
import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.info

@Suppress("PrivatePropertyName")
/**
 * Used to schedule and un schedule sending of SMS at certain date.
 */
class SmsAlarmScheduler(val context: Context) : SendingSmsScheduler, AnkoLogger {
    private val FREQUENCY_INTERVAL = 1000 * 5L//for production, switch to 1 month

    override fun scheduleNextSms(sms: Sms) {
        info { "Schedule Next sms" }
        getAlarmManager().setInexactRepeating(AlarmManager.RTC_WAKEUP, sms.date.time,
                FREQUENCY_INTERVAL, pendingIntentToFireOnAlarmEvent(sms.number))
        return
    }

    override fun unscheduleNextSms(smsNumber: Int){
        info("Un Schedule Next sms")
        getAlarmManager().cancel(pendingIntentToFireOnAlarmEvent(smsNumber))
    }

    private fun getAlarmManager() = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager

    private fun pendingIntentToFireOnAlarmEvent(smsNumber: Int): PendingIntent? {
        val intent = Intent(context, AlarmServiceReceiver::class.java)
        intent.putExtra(AlarmServiceReceiver.SMS_NUMBER_KEY, smsNumber)
        return PendingIntent.getBroadcast(
                context,
                smsNumber,
                intent,
                PendingIntent.FLAG_UPDATE_CURRENT
        )
    }
}

